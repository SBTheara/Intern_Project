package com.intern.project.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.intern.project.service.GoogleAuthenticationService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class GoogleAuthenticationController {

  private final GoogleAuthenticationService googleAuthenticationService;

  @GetMapping("/callback")
  public ResponseEntity<GoogleTokenResponse> callback(@RequestParam("code") String code)
      throws IOException {
    return new ResponseEntity<>(googleAuthenticationService.getToken(code), HttpStatus.OK);
  }

  @GetMapping("/url")
  public ResponseEntity<String> auth() {
    return googleAuthenticationService.getAuth();
  }

  @GetMapping("/refresh/token")
  public ResponseEntity<GoogleTokenResponse> refreshToken(
      @RequestParam("request-param") String refreshToken) throws IOException {
    return new ResponseEntity<>(
        googleAuthenticationService.getNewTokenWith(refreshToken), HttpStatus.OK);
  }
}
