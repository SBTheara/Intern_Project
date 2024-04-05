package com.intern.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String address;
  private String phone;
  private boolean isEnable;
  private boolean isEmailVerified;
}
