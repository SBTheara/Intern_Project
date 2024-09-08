package com.intern.project.component;

import com.intern.project.config.properties.KeycloakConfigProperty;
import com.intern.project.dto.RegistrationRequest;
import com.intern.project.dto.RegistrationResponse;
import jakarta.ws.rs.core.Response;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KeycloakComponent {

  private final KeycloakConfigProperty keycloakConfigProperty;

  public KeycloakBuilder getInstance() {
    return KeycloakBuilder.builder()
        .serverUrl(keycloakConfigProperty.getServerUrl())
        .realm(keycloakConfigProperty.getRealm())
        .clientId(keycloakConfigProperty.getClientId())
        .clientSecret(keycloakConfigProperty.getClientSecret());
  }

  public AccessTokenResponse getTokenInstance(String email, String password) {
    return this.getInstance()
        .username(email)
        .password(password)
        .grantType(OAuth2Constants.PASSWORD)
        .build()
        .tokenManager()
        .getAccessToken();
  }

  public ResponseEntity<RegistrationResponse> register(RegistrationRequest request) {
    CredentialRepresentation credential = this.getPasswordCredential(request.getPassword());
    final var result = getRegisterInstance(this.getUserRepresentation(request, credential));
    return new ResponseEntity<>(
        RegistrationResponse.builder()
            .statusCode(result.getStatus())
            .statusDetail(result.getStatusInfo())
            .build(),
        HttpStatusCode.valueOf(result.getStatus()));
  }

  private UserRepresentation getUserRepresentation(
      RegistrationRequest request, CredentialRepresentation credential) {
    UserRepresentation user = new UserRepresentation();
    user.setUsername(request.getUserName());
    user.setFirstName(request.getFirstname());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setCredentials(Collections.singletonList(credential));
    user.setEnabled(true);
    return user;
  }

  private Response getRegisterInstance(UserRepresentation user) {
    return getInstance()
        .username(keycloakConfigProperty.getUsername())
        .password(keycloakConfigProperty.getPassword())
        .build()
        .realm(keycloakConfigProperty.getRealm())
        .users()
        .create(user);
  }

  private CredentialRepresentation getPasswordCredential(String password) {
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue(password);
    return passwordCredentials;
  }
}
