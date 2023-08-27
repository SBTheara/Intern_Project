package com.example.mysmallproject.service.implement;

import com.example.mysmallproject.Exception.ApiRequestException;
import com.example.mysmallproject.entity.Products;
import com.example.mysmallproject.entity.Users;
import com.example.mysmallproject.repository.UsersRepository;
import com.example.mysmallproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersImplement implements UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }
    @Override
    public List<Users> getUsers(){
        return usersRepository.findAll();
    }

    @Override
    public Users getUserById(int id) {
        return usersRepository.findById(id).orElseThrow(()->new ApiRequestException("Not found for this user"));
    }
    @Override
    public Users updateUsers(Users user, int id) {
        Users users = usersRepository.findById(id).orElseThrow(()->new ApiRequestException("Not found for this user"));
        users.setFirstname(user.getFirstname());
        users.setLastname(user.getLastname());
        users.setEmail(user.getEmail());
        users.setPassword(user.getPassword());
        users.setAddress(user.getAddress());
        users.setPhone(user.getPhone());
        users.setType(user.getType());
        users.setCreate_at(user.getCreate_at());
        usersRepository.save(users);
        return users;
    }
    @Override
    public void deleteUser(int id) {
        usersRepository.deleteById(id);
    }
}
