package com.intern.project.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "google.authentication")
public class GoogleAuthConfigProperty {
  private String clientId;
  private String clientSecret;
  private String callBackUri;
  private String grantType;
  private String jwkSetUri;
  private String issuerUri;
}
