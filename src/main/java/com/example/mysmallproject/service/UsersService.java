package com.example.mysmallproject.service;

import com.example.mysmallproject.dto.UserDTO;
import com.example.mysmallproject.dto.UserRegistrationDTO;
import com.example.mysmallproject.entity.User;
import com.example.mysmallproject.repository.UsersRepository;
import com.example.mysmallproject.component.UserSpecification;
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

  @Override
  public UserDTO save(UserRegistrationDTO userRegistrationDTO) {
    try {
      User userRequest = modelMapper.map(userRegistrationDTO, User.class);
      User user = usersRepository.save(userRequest);
      log.debug("The user has been added !!! ");
      return modelMapper.map(user, UserDTO.class);
    } catch (IllegalStateException exception) {
      log.error("Could not add this user !!! ");
      return null;
    }
  }

  @Override
  public UserDTO update(UserRegistrationDTO userRegistrationDTO, long id) {
    try {
      User users = usersRepository.findById(id).get();
      users = modelMapper.map(userRegistrationDTO, users.getClass());
      usersRepository.save(users);
      log.debug("This user's information has updated !!! ");
      return modelMapper.map(users, UserDTO.class);
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
