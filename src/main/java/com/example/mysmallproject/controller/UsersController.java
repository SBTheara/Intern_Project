package com.example.mysmallproject.controller;
import com.example.mysmallproject.dto.UserDTO;
import com.example.mysmallproject.dto.UserRegistrationDTO;
import com.example.mysmallproject.exception.GlobalExceptionHandler;
import com.example.mysmallproject.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = {"*"})
@Validated
@RestController
@Slf4j
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;
    @PostMapping(value = "/addNewUsers")
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO){
        try{
            log.debug("The user has been added !!! ");
            return new ResponseEntity<>(usersService.save(userRegistrationDTO),HttpStatus.CREATED);
        }catch (IllegalStateException exception){
            log.error("Could not add this user !!! ");
            throw new IllegalStateException(exception.getMessage());
        }
    }
    @PutMapping(value = "/updateUsers/{id}")
    public ResponseEntity<String> updateUser (@RequestBody UserRegistrationDTO userRegistrationDTO, @PathVariable(name = "id") int id){
        try{
            usersService.update(userRegistrationDTO, id);
            log.debug("This user's information has updated !!! ");
            return new ResponseEntity<>("Update successful", HttpStatus.OK);
        }catch (IllegalStateException exception) {
            log.error("This user's information not found !!! ");
            throw new IllegalStateException(exception.getMessage());
        }

    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable(name = "id") int id){
        usersService.delete(id);
        log.debug(GlobalExceptionHandler.DELETED);
        return new ResponseEntity<>("Successfully deleted an user",HttpStatus.OK);
    }
    @GetMapping(value = "/filterAndSearch")
    public ResponseEntity<Page<UserDTO>> filter(
            @RequestParam(name = "address",required = false) String address,
            @RequestParam(name = "search",required = false) String search,
            @RequestParam(name = "sortBy",required = false,defaultValue = "id") String sortBy,
            @RequestParam(name = "offset",required = false,defaultValue = "0") int offset,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize
    )
    {
        try{
            Page<UserDTO> userDTOPage =  usersService.filter(address,search, sortBy, offset, pageSize);
            log.debug("Successfully get the user information !!! ");
            return ResponseEntity.ok().body(userDTOPage);
        }catch (IllegalStateException exception){
            log.error("Something went wrong while getting user information !!! ");
            throw new IllegalStateException(exception.getMessage());
        }
    }
}
