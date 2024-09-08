package com.intern.project.service;

import com.intern.project.component.KeycloakComponent;
import com.intern.project.dto.LoginRequest;
import com.intern.project.dto.LoginResponse;
import com.intern.project.dto.RegistrationRequest;
import com.intern.project.dto.RegistrationResponse;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/** Get authentication and authorization with keycloak */
@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {

  private final KeycloakComponent keycloakComponent;

  /**
   * Method use for authentication with keycloak by username and password
   *
   * @param loginRequest The request for authentication with keycloak by username and password
   * @return @{@link LoginResponse}
   */
  public LoginResponse login(LoginRequest loginRequest) {
    final var tokenResponse =
        keycloakComponent.getTokenInstance(loginRequest.getEmail(), loginRequest.getPassword());
    return LoginResponse.builder()
        .accessToken(tokenResponse.getToken())
        .refreshToken(tokenResponse.getRefreshToken())
        .expireAt(tokenResponse.getExpiresIn())
        .tokenType(tokenResponse.getTokenType())
        .build();
  }

  public ResponseEntity<RegistrationResponse> register(RegistrationRequest request) {
    return keycloakComponent.register(request);
  }
}
