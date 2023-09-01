package com.example.mysmallproject.controller;
import com.example.mysmallproject.entity.User;
import com.example.mysmallproject.repository.UsersRepository;
import com.example.mysmallproject.service.UsersService;
import com.example.mysmallproject.specification.UserSpecification;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.data.jpa.domain.Specification.where;
@CrossOrigin(origins = {"*"})
@RestController
@Slf4j
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;
    private final UsersRepository usersRepository;
    @PostMapping(value = "/addNewUsers")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        log.debug("New user has been added !!!!!");
        return new ResponseEntity<>(usersService.saveUser(user), HttpStatus.CREATED);
    }
    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<List<User>> getUsers(){
        log.debug("Get all users !!!!!");
        return new ResponseEntity<>(usersService.getUser(),HttpStatus.OK);
    }
    @GetMapping(value = "/getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") int id){
        log.debug("User found for id: {}!!!!!",id);
        return new ResponseEntity<>(usersService.getUserById(id),HttpStatus.OK);
    }
    @PutMapping(value = "/updateUsers/{id}")
    public ResponseEntity<String> updateUser (@RequestBody User user, @PathVariable(name = "id") int id){
        usersService.updateUser(user,id);
        log.debug("User has been updated !!!!");
        return new ResponseEntity<>("Update successful",HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable(name = "id") int id){
        usersService.deleteUser(id);
        log.debug("This user was deleted !!!!!!");
        return new ResponseEntity<>("Successfully deleted an user",HttpStatus.OK);
    }
    @GetMapping(value = "/filterAndSearch")
    public Page<User> filter(
            @RequestParam(name = "address",required = false) String address,
            @RequestParam(name = "type",required = false) String type,
            @RequestParam(name = "search",required = false) String search,
            @RequestParam(name = "sortBy",required = false,defaultValue = "firstname") String sortBy,
            @RequestParam(name = "offset",required = false,defaultValue = "0") int offset,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize
    )
    {
        Pageable pageable = PageRequest.of(offset,pageSize,Sort.by(Sort.Direction.ASC,sortBy));
        if(address.isEmpty()&&type.isEmpty()){
            log.debug("Get all users !!!");
            return usersRepository.findAll(pageable);
        }else{
            log.debug("Get the user has match = {} and from page has offset : {} and pagesize : {}",search,offset,pageSize);
            return usersRepository.findAll(where(UserSpecification.filterAndSearch(address,type,search)),pageable);
        }
    }
}
