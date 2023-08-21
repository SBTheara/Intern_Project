package com.example.mysmallproject.controller;

import com.example.mysmallproject.entity.Users;
import com.example.mysmallproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Users_Controller {
    @Autowired
    private UsersService usersService;
    @PostMapping(value = "/addnewUsers")
    public ResponseEntity<Users> saveUser(@RequestBody Users user){
        return new ResponseEntity<>(usersService.saveUser(user), HttpStatus.CREATED);
    }
    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<List<Users>> getUsers(){
        return new ResponseEntity<>(usersService.getUsers(),HttpStatus.OK);
    }
    @PutMapping(value = "/updateUsers/{id}")
    public ResponseEntity<String> updateUser (@RequestBody Users user, @PathVariable(name = "id") int id){
        usersService.updateUsers(user,id);
        return new ResponseEntity<>("Update successful",HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable(name = "id") int id){
        usersService.deleteUser(id);
        return new ResponseEntity<>("Successfully deleted an user",HttpStatus.OK);
    }
}
