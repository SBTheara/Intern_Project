package com.example.mysmallproject.controller;

import com.example.mysmallproject.entity.Users;
import com.example.mysmallproject.repository.UsersRepository;
import com.example.mysmallproject.service.UsersService;
import com.example.mysmallproject.specifications.UserSpacifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@CrossOrigin(origins = {"http://localhost:4200", "http://10.0.2.2:8080"})
@RestController
@RequestMapping(value = "/users")
public class Users_Controller {
    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;
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


    @GetMapping(value = "/get/{field}")
    public List<Users> GetUserByFirstnameOrLastnameOrEmail(
            @PathVariable(name = "field") String field/*,
            @PathVariable(name = "email") String email,
            @PathVariable(name = "address") String address*/
    ){
        return usersRepository.findAll(where(UserSpacifications.getinfo(field)));
    }
}
