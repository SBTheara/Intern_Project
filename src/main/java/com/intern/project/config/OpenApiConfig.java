package com.intern.project.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Theara",
                        email = "thearasambath112@gmail.com",
                        url = "https://www.facebook.com/sambath.theara.52/"
                ),
                description = "Hello world developer",
                title = "Test api with keycloak",
                version = "1.0",
                termsOfService = "term of service"
        ),
        security = {
                @SecurityRequirement(
                        name = "BearerToken"
                )
        }


)
@SecurityScheme(
        name = "BearerToken",
        description = "Jwt oauth2 description",
        scheme = "openIdConnect",
        type = SecuritySchemeType.OPENIDCONNECT,
        bearerFormat = "JWT"
)
@Configuration
public class OpenApiConfig {
}
