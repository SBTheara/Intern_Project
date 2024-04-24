package com.intern.project.config;

import com.intern.project.config.properties.KeycloakConfigProperty;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = KeycloakConfigProperty.class)
public class OpenAPISecurityConfig {

  private static final String OAUTH_SCHEME_NAME = "Product-service-management";

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .components(new Components().addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
        .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME))
        .info(
            new Info()
                .title("Product Management Service")
                .description("A service providing product sell management.")
                .version("1.0"));
  }

  private SecurityScheme createOAuthScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
  }
}
