package com.intern.project.service;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.intern.project.config.properties.GoogleAuthConfigProperty;
import com.intern.project.entity.User;
import com.intern.project.repository.UsersRepository;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@EnableConfigurationProperties(value = GoogleAuthConfigProperty.class)
public class GoogleAuthenticationService {
  private static final List<String> SCOPES = List.of("openid", "email", "profile");
  private static final String ACCESS_TYPE = "access_type";
  private static final String OFFLINE = "offline";
  private static final String PROMPT = "prompt";
  private static final String CONSENT = "consent";
  private final UsersRepository usersRepository;
  private final String clientId;
  private final String clientSecret;
  private final String callBackUri;

  public GoogleAuthenticationService(
      UsersRepository usersRepository, GoogleAuthConfigProperty googleAuthConfigProperty) {
    this.usersRepository = usersRepository;
    this.clientId = googleAuthConfigProperty.getClientId();
    this.clientSecret = googleAuthConfigProperty.getClientSecret();
    this.callBackUri = googleAuthConfigProperty.getCallBackUri();
  }

  public GoogleTokenResponse getToken(String code) throws IOException, GeneralSecurityException {

    GoogleIdTokenVerifier verifier =
        new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
            .setAudience(Collections.singletonList(clientId))
            .build();

    GoogleTokenResponse googleTokenResponse =
        new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                new GsonFactory(),
                clientId,
                clientSecret,
                code,
                callBackUri)
            .execute();

    User user = new User();

    GoogleIdToken idToken = verifier.verify(googleTokenResponse.getIdToken());

    if (Objects.isNull(idToken)) {
      googleTokenResponse.clear();
      googleTokenResponse.set("Unauthorized", "ID token not exists");
      log.error("Invalid ID token.");
    }

    GoogleIdToken.Payload payload = idToken.getPayload();

    if (payload.getEmail().startsWith("theara")) {
      googleTokenResponse.clear();
      googleTokenResponse.set("Unauthorized", "Invalid email");
      return googleTokenResponse;
    }

    Optional<User> userOptional = this.usersRepository.findByEmail(payload.getEmail());

    if (userOptional.isEmpty()) {
      // Get profile information from payload
      user.setEmail(payload.getEmail());
      user.setEmailVerified(payload.getEmailVerified());
      user.setUsername((String) payload.get("name"));
      user.setAddress((String) payload.get("locale"));
      user.setFirstName((String) payload.get("family_name"));
      user.setLastName((String) payload.get("given_name"));
      user.setUserSubjectId(payload.getSubject());
      // Use or store profile information
      usersRepository.save(user);
    }

    return googleTokenResponse;
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
