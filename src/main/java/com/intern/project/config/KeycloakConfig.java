package com.intern.project.config;

import com.intern.project.config.properties.KeycloakConfigProperty;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = KeycloakConfigProperty.class)
public class KeycloakConfig {

  private final String realm;
  private final String serverURL;
  private final String clientID;
  private final String clientSecret;

  public KeycloakConfig(KeycloakConfigProperty keycloakConfigProperty) {
    this.realm = keycloakConfigProperty.getRealm();
    this.serverURL = keycloakConfigProperty.getServerUrl();
    this.clientID = keycloakConfigProperty.getClientId();
    this.clientSecret = keycloakConfigProperty.getClientSecret();
  }

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .realm(realm)
        .serverUrl(serverURL)
        .clientId(clientID)
        .clientSecret(clientSecret)
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .build();
  }
}
