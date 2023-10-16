package com.intern.project.controller;

import com.intern.project.dto.PageResponse;
import com.intern.project.dto.UserDTO;
import com.intern.project.dto.UserRegistrationDTO;
import com.intern.project.entity.User_;
import com.intern.project.service.UsersService;
import com.intern.project.utils.PageRequest;
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

  @PostMapping
  public ResponseEntity<UserDTO> saveUser(
      @Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
    return new ResponseEntity<>(usersService.save(userRegistrationDTO), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<UserDTO> updateUser(
      @Valid @RequestBody UserRegistrationDTO userRegistrationDTO,
      @PathVariable(name = "id") int id) {
    return new ResponseEntity<>(usersService.update(userRegistrationDTO, id), HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<String> deleteUsers(@PathVariable(name = "id") int id) {
    usersService.delete(id);
    return new ResponseEntity<>("Successfully deleted an user", HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<PageResponse<UserDTO>> filter(
      @RequestParam(required = false) String address,
      @RequestParam(required = false) String search,
      @RequestParam(required = false, defaultValue = "desc") String direction,
      @RequestParam(required = false, defaultValue = User_.ID) String sortBy,
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "10") int pageSize) {
    PageRequest request =
        PageRequest.builder()
            .page(offset - 1)
            .size(pageSize)
            .direction(direction)
            .sort(sortBy)
            .build();

    Page<UserDTO> userDTOPage = usersService.filter(address, search, request);
    return new ResponseEntity<>(
        new PageResponse<>(
            offset, pageSize, userDTOPage.getTotalElements(), userDTOPage.getContent()),
        HttpStatus.OK);
  }
}
