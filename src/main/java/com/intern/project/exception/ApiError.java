package com.intern.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiError {
  private HttpStatus status;
  private String message;
  private Object errors;
}
