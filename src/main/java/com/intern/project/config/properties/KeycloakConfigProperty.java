package com.intern.project.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakConfigProperty {

  private String serverUrl;
  private String realm;
  private String clientId;
  private String clientSecret;
  private String authUrl;
  private String jwkSetUri;
  private String issuerUri;
  private String tokenUrl;
  private String username;
  private String password;
}
