package com.intern.project.controller;

import com.intern.project.dto.LoginRequest;
import com.intern.project.dto.LoginResponse;
import com.intern.project.dto.RegistrationRequest;
import com.intern.project.dto.RegistrationResponse;
import com.intern.project.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final KeycloakService keycloakService;

  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(keycloakService.login(loginRequest));
  }

  @PostMapping(value = "/register")
  public ResponseEntity<RegistrationResponse> register(RegistrationRequest request) {
    return keycloakService.register(request);
  }
}
