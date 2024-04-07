package com.intern.project.service;

import com.intern.project.config.properties.KeycloakConfigProperty;
import com.intern.project.dto.UserDTO;
import com.intern.project.dto.UserRegistrationDTO;
import com.intern.project.entity.User;
import com.intern.project.exception.UserNotFoundException;
import com.intern.project.repository.UsersRepository;
import com.intern.project.utils.UserSpecification;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@EnableConfigurationProperties(KeycloakConfigProperty.class)
public class UsersService {
  private final UsersRepository usersRepository;
  private final ModelMapper modelMapper;
  private final Keycloak keycloak;
  private final String clientId;
  private final String realm;

  public UsersService(
      UsersRepository usersRepository,
      ModelMapper modelMapper,
      Keycloak keycloak,
      KeycloakConfigProperty keycloakConfigProperty) {
    this.usersRepository = usersRepository;
    this.modelMapper = modelMapper;
    this.keycloak = keycloak;
    this.clientId = keycloakConfigProperty.getClientId();
    this.realm = keycloakConfigProperty.getRealm();
  }

  public UserDTO save(UserRegistrationDTO userRegistrationDTO) {
    var usersResource = keycloak.realm(realm).users();

    UserRepresentation userRepresentation = this.getUserRepresentation(userRegistrationDTO);
    userRepresentation.setEmailVerified(false);
    userRepresentation.setRequiredActions(List.of("VERIFY_EMAIL"));
    String userReferenceId;


    try (var response = usersResource.create(userRepresentation)) {
      if (response.getStatus() != 201) {
        throw new IllegalArgumentException(response.getStatusInfo().getReasonPhrase());
      }
      String locationUri = (String) response.getMetadata().get("Location").get(0);
      userReferenceId = locationUri.substring(locationUri.lastIndexOf("/") + 1);
    }
    var userSendMail = usersResource.searchByUsername(userRegistrationDTO.getUsername(), true).stream()
        .findFirst()
            .orElse(null);

    var userId = userSendMail.getId();
    User user = this.modelMapper.map(userRegistrationDTO, User.class);
    user.setPassword(new BCryptPasswordEncoder().encode(userRegistrationDTO.getPassword()));
    user.setReferenceId(userReferenceId);
    user.setEnable(false);
    log.debug("The user has been added !!! ");
        this.sendVerificationLink(userId, usersResource);
    return this.modelMapper.map(usersRepository.save(user), UserDTO.class);
  }

    private void sendVerificationLink(String userReferenceId, UsersResource usersResource) {
      usersResource.get(userReferenceId).sendVerifyEmail();
    }

  private UserRepresentation getUserRepresentation(UserRegistrationDTO userRegistrationDTO) {
    UserRepresentation userRepresentation = new UserRepresentation();
    this.modelMapper.map(userRegistrationDTO, userRepresentation);
    return userRepresentation;
  }

  public UserDTO update(UserRegistrationDTO userRegistrationDTO, long id) {
    User users = usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    this.modelMapper.map(userRegistrationDTO, users);
    usersRepository.save(users);
    log.debug("This user's information has updated !!! ");
    return this.modelMapper.map(users, UserDTO.class);
  }

  public void delete(long id) {
    User user = usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    usersRepository.delete(user);
  }

  public Page<UserDTO> filter(
      String address, String search, com.intern.project.utils.PageRequest request) {
    Pageable pageable = request.toPageable();
    Specification<User> specification = Specification.where(null);
    boolean isFilterAddress = StringUtils.hasText(address);
    if (isFilterAddress) {
      specification = specification.and(UserSpecification.withFilterAddress(address));
    }
    boolean isFilterSearch = StringUtils.hasText(search);
    if (isFilterSearch) {
      specification = specification.and(UserSpecification.withFilterSearch(search));
    }
    Page<User> userPage = usersRepository.findAll(specification, pageable);
    List<UserDTO> userDTOS =
        userPage.getContent().stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
    log.debug("Successfully get the user information !!! ");
    return new PageImpl<>(userDTOS, pageable, userPage.getTotalElements());
  }
}
