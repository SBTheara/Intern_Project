package com.intern.project.controller;

import com.intern.project.dto.UserDTO;
import com.intern.project.dto.UserRegistrationDTO;
import com.intern.project.entity.User_;
import com.intern.project.service.UsersService;
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
    public ResponseEntity<UserDTO> updateUser(
            @Valid @RequestBody UserRegistrationDTO userRegistrationDTO, @PathVariable(name = "id") int id) {
        return new ResponseEntity<>(usersService.update(userRegistrationDTO, id), HttpStatus.OK);
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
