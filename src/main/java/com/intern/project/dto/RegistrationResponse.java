package com.intern.project.dto;

import jakarta.ws.rs.core.Response;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegistrationResponse {
  private int statusCode;
  private Response.StatusType statusDetail;
}
