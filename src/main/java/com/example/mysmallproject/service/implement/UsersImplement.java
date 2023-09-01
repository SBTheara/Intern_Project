package com.example.mysmallproject.service.implement;

import com.example.mysmallproject.entity.User;
import com.example.mysmallproject.exception.RequestException;
import com.example.mysmallproject.repository.UsersRepository;
import com.example.mysmallproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersImplement implements UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public User saveUser(User user) {
        return usersRepository.save(user);
    }
    @Override
    public List<User> getUser(){
        return usersRepository.findAll();
    }
    @Override
    public User getUserById(int id) {
        return usersRepository.findById(id).orElseThrow(()->new RequestException("Not found for this user"));
    }
    @Override
    public User updateUser(User user, int id) {
        User users = usersRepository.findById(id).orElseThrow(()->new RequestException("Not found for this user"));
        users.setFirstName(user.getFirstName());
        users.setLastName(user.getLastName());
        users.setEmail(user.getEmail());
        users.setPassword(user.getPassword());
        users.setAddress(user.getAddress());
        users.setPhone(user.getPhone());
        users.setType(user.getType());
        users.setCreateAt(user.getCreateAt());
        usersRepository.save(users);
        return users;
    }
    @Override
    public void deleteUser(int id) {
        usersRepository.deleteById(id);
    }
}
