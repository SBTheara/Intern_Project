package com.intern.project.service;


import com.intern.project.component.DTOConverter;
import com.intern.project.dto.UserDTO;
import com.intern.project.dto.UserRegistrationDTO;
import com.intern.project.entity.User;
import com.intern.project.exception.UserNotFoundException;
import com.intern.project.repository.UsersRepository;
import com.intern.project.utils.UserSpecification;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {
  private final UsersRepository usersRepository;
  private final ModelMapper modelMapper;
  private final DTOConverter<User, UserDTO> dtoConverter;

  public UserDTO save(UserRegistrationDTO userRegistrationDTO) {
    User userRequest = dtoConverter.convertToClass(userRegistrationDTO, User.class);
    User user = usersRepository.save(userRequest);
    log.debug("The user has been added !!! ");
    return dtoConverter.convertToDTO(user, UserDTO.class);
  }

  public UserDTO update(UserRegistrationDTO userRegistrationDTO, long id) {
    User users = usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    this.modelMapper.map(userRegistrationDTO, users);
    usersRepository.save(users);
    log.debug("This user's information has updated !!! ");
    return dtoConverter.convertToDTO(users, UserDTO.class);
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
