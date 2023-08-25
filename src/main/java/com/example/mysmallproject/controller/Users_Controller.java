package com.example.mysmallproject.controller;
import com.example.mysmallproject.entity.Users;
import com.example.mysmallproject.repository.UsersRepository;
import com.example.mysmallproject.service.UsersService;
import com.example.mysmallproject.specifications.UserSpacifications;
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
    @GetMapping(value = "/search")
    public Page<Users> Search(
            @RequestParam(name = "field") String field,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "pagesize",required = false,defaultValue = "5") int pagesize
    ){
        Pageable pageable = PageRequest.of(offset,pagesize);
        return usersRepository.findAll(where(UserSpacifications.search(field)),pageable);
    }
    @GetMapping(value = "/filterAndSearch")
    public Page<Users>  filter (
            @RequestParam(name = "address",required = false) String address,
            @RequestParam(name = "type",required = false) String type,
            @RequestParam(name = "search",required = false) String search,
            @RequestParam(name = "offset",required = false) int offset,
            @RequestParam(name = "pagesize",required = false) int pagesize
    )
    {
        Pageable pageable = PageRequest.of(offset,pagesize,Sort.by(Sort.Direction.ASC,"firstname"));
            return usersRepository.findAll(where(UserSpacifications.filterAndSearch(address,type,search)),pageable);
    }
}
