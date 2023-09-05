package com.example.mysmallproject.controller;

import com.example.mysmallproject.dto.UserDTO;
import com.example.mysmallproject.dto.UserRegistrationDTO;
import com.example.mysmallproject.entity.User_;
import com.example.mysmallproject.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @PostMapping(value = "/add-new-users")
    public ResponseEntity<UserDTO> saveUser(
            @Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        return new ResponseEntity<>(usersService.save(userRegistrationDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update-users/{id}")
    public ResponseEntity<String> updateUser(
            @RequestBody UserRegistrationDTO userRegistrationDTO, @PathVariable(name = "id") int id) {
        usersService.update(userRegistrationDTO, id);
        return new ResponseEntity<>("Update successful", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable(name = "id") int id) {
        usersService.delete(id);
        return new ResponseEntity<>("Successfully deleted an user", HttpStatus.OK);
    }

    @GetMapping(value = "/filter-and-search")
    public ResponseEntity<Page<UserDTO>> filter(
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "sort-by", required = false, defaultValue = User_.ID) String sortBy,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page-size", required = false, defaultValue = "10") int pageSize) {
        Page<UserDTO> userDTOPage = usersService.filter(address, search, sortBy, offset, pageSize);
        return ResponseEntity.ok().body(userDTOPage);
    }
}
