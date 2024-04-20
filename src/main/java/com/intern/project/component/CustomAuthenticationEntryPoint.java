package com.intern.project.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final String MESSAGE = "You don't have permission to access this ";

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write(MESSAGE);
  }
}
