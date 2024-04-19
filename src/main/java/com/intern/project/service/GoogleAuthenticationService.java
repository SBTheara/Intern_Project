package com.intern.project.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.intern.project.config.properties.GoogleAuthConfigProperty;
import java.io.IOException;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@EnableConfigurationProperties(value = GoogleAuthConfigProperty.class)
public class GoogleAuthenticationService {

  private static final List<String> SCOPES = List.of("openid", "email", "profile");
  private static final String ACCESS_TYPE = "access_type";
  private static final String OFFLINE = "offline";
  private static final String PROMPT = "prompt";
  private static final String CONSENT = "consent";
  private final String clientId;
  private final String clientSecret;
  private final String callBackUri;

  public GoogleAuthenticationService(GoogleAuthConfigProperty googleAuthConfigProperty) {
    this.clientId = googleAuthConfigProperty.getClientId();
    this.clientSecret = googleAuthConfigProperty.getClientSecret();
    this.callBackUri = googleAuthConfigProperty.getCallBackUri();
  }

  public GoogleTokenResponse getToken(String code) throws IOException {
    return new GoogleAuthorizationCodeTokenRequest(
            new NetHttpTransport(), new GsonFactory(), clientId, clientSecret, code, callBackUri)
        .execute();
  }

  public ResponseEntity<String> getAuth() {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    var httpEntity = new HttpEntity<>(headers);
    return restTemplate.exchange(
        new GoogleAuthorizationCodeRequestUrl(clientId, callBackUri, SCOPES)
            .set(ACCESS_TYPE, OFFLINE)
            .set(PROMPT, CONSENT)
            .toURI(),
        HttpMethod.POST,
        httpEntity,
        String.class);
  }

  public GoogleTokenResponse getNewTokenWith(String refreshToken) throws IOException {
    return new GoogleRefreshTokenRequest(
            new NetHttpTransport(), new GsonFactory(), refreshToken, clientId, clientSecret)
        .execute();
  }
}
