package com.example.mysmallproject.service;

import com.example.mysmallproject.entity.User;

import java.util.List;

public interface UsersService {
    User saveUser(User user);
    List<User> getUser();
    User getUserById(int id);
    User updateUser(User user, int id);
    void deleteUser(int id);
}
