package com.example.mysmallproject.service;

import com.example.mysmallproject.entity.Products;
import com.example.mysmallproject.entity.Users;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UsersService {
    Users saveUser(Users user);
    List<Users> getUsers();
    Users updateUsers(Users user,int id);
    void deleteUser(int id);
}
