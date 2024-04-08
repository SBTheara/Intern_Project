package com.intern.project.config;

import com.intern.project.component.CustomAuthenticationEntryPoint;
import com.intern.project.component.JwtTokenConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final ClientRegistrationRepository clientRegistrationRepository;
  private final OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;
  private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtTokenConverter());
    return http.cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .oauth2ResourceServer(
            oauth ->
                oauth
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/users/add-new-users",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/swagger-ui",
                        "/swagger-ui/**",
                        "/v1/users/create",
                        "/login/oauth2/code/google",
                        "/v1/login")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .oauth2Login(
            oauth2Login ->
                oauth2Login
                    .clientRegistrationRepository(this.clientRegistrationRepository)
                    .authorizedClientRepository(this.oAuth2AuthorizedClientRepository)
                    .authorizedClientService(this.oAuth2AuthorizedClientService)
                    .authorizationEndpoint(
                        authorizationEndpointConfig ->
                            authorizationEndpointConfig.baseUri("http://localhost:8888"))
                    .redirectionEndpoint(
                        redirectionEndpointConfig ->
                            redirectionEndpointConfig.baseUri(
                                "http://localhost:8888/login/oauth2/code/google")))
        .build();
  }
}
