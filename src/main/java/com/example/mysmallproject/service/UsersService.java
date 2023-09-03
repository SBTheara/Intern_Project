package com.example.mysmallproject.service;

import com.example.mysmallproject.dto.UserDTO;
import com.example.mysmallproject.dto.UserRegistrationDTO;
import com.example.mysmallproject.entity.User;

import java.util.List;

public interface UsersService {
    UserDTO saveUser(UserRegistrationDTO userRegistrationDTO);
    List<UserDTO> getUser();
    UserDTO getUserById(int id);
    UserDTO updateUser(UserRegistrationDTO userRegistrationDTO, int id);
    void deleteUser(int id);
}
