package com.intern.project.service;

import com.intern.project.component.DTOConverter;
import com.intern.project.utils.UserSpecification;
import com.intern.project.dto.UserDTO;
import com.intern.project.dto.UserRegistrationDTO;
import com.intern.project.repository.UsersRepository;
import com.intern.project.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService implements HelperGenerics<UserDTO, UserRegistrationDTO> {
  private final UsersRepository usersRepository;
  private final ModelMapper modelMapper;
  private final DTOConverter<User,UserDTO> dtoConverter;
  /*

   */
  @Override
  public UserDTO save(UserRegistrationDTO userRegistrationDTO) {
      User userRequest = dtoConverter.convertToClass(userRegistrationDTO,User.class);
      User user = usersRepository.save(userRequest);
      log.debug("The user has been added !!! ");
      return dtoConverter.convertToDTO(user,UserDTO.class);
  }
  @Override
  public UserDTO update(UserRegistrationDTO userRegistrationDTO, long id) {
    try {
      User users = usersRepository.findById(id).get();
      users.setFirstName(userRegistrationDTO.getFirstName());
      users.setLastName(userRegistrationDTO.getLastName());
      users.setEmail(userRegistrationDTO.getEmail());
      users.setPassword(userRegistrationDTO.getPassword());
      users.setAddress(userRegistrationDTO.getAddress());
      users.setPhone(userRegistrationDTO.getPhone());
      users.setCreateAt(userRegistrationDTO.getCreateAt());
      usersRepository.save(users);
      log.debug("This user's information has updated !!! ");
      return dtoConverter.convertToDTO(users,UserDTO.class);
    } catch (IllegalStateException exception) {
      log.error("This user's information not found !!! ");
      return null;
    }
  }
  @Override
  public void delete(long id) {
    try {
      usersRepository.deleteById(id);
      log.debug("This user has been deleted !!!");
    } catch (IllegalStateException exception) {
      log.error("Failed to delete this user !!!");
    }
  }
  public Page<UserDTO> filter(
      String address, String search, String sortBy, int offset, int pageSize) {
    Pageable pageable = PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
    try {
      List<UserDTO> userDTOS =
          usersRepository
              .findAll(where(UserSpecification.filterAndSearch(address, search)), pageable)
              .stream()
              .map(p -> modelMapper.map(p, UserDTO.class))
              .toList();
      log.debug("Successfully get the user information !!! ");
      return new PageImpl<>(userDTOS, Pageable.unpaged(), userDTOS.size());
    } catch (IllegalStateException exception) {
      log.error("Something went wrong while getting user information !!! ");
      return null;
    }
  }
}
