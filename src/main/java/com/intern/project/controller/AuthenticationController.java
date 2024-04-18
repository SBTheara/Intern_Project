package com.intern.project.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  @Value("${spring.security.oauth2.client.registration.google.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.google.client-secret}")
  private String clientSecret;

  @GetMapping("/callback")
  public ResponseEntity<?> callback(@RequestParam("code") String code) throws IOException {
    return new ResponseEntity<>(
        new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                new GsonFactory(),
                clientId,
                clientSecret,
                List.of("openid", "email", "profile"))
            .build()
            .newTokenRequest(code)
            .setRedirectUri("http://localhost:8888/api/v1/auth/callback")
            .execute(),
        HttpStatus.OK);
  }

  @GetMapping("/url")
  public ResponseEntity<String> auth() {
    RestTemplate restTemplate = new RestTemplate();
    return new ResponseEntity<>(
        restTemplate.getForObject(
            new GoogleAuthorizationCodeRequestUrl(
                    clientId,
                    "http://localhost:8888/api/v1/auth/callback",
                    Arrays.asList("openid", "email", "profile"))
                .build(),
            String.class),
        HttpStatus.OK);
  }
}
