package com.intern.project.controller;

import com.intern.project.dto.EmailPassResponse;
import com.intern.project.dto.EmailPasswordRequest;
import com.intern.project.entity.User;
import com.intern.project.repository.UsersRepository;
import com.intern.project.utils.JwtUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class EmailPasswordController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final UsersRepository usersRepository;

  @PostMapping("/login")
  public ResponseEntity<EmailPassResponse> login(
      @RequestBody EmailPasswordRequest emailPasswordRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                emailPasswordRequest.getEmail(), emailPasswordRequest.getPassword()));
    Optional<User> userOptional = usersRepository.findByEmail(authentication.getName());
    if (userOptional.isEmpty()) {
      throw new IllegalArgumentException("User not found");
    }

    User user = userOptional.get();
    return ResponseEntity.ok(jwtUtil.generateToken(user));
  }
}
