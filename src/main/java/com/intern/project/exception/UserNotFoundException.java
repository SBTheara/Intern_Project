package com.intern.project.exception;

public class UserNotFoundException extends IllegalStateException {
  public UserNotFoundException(Long id) {
    super("User not found for id : " + id);
  }
}
