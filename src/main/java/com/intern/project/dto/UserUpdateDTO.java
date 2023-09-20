package com.intern.project.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class UserUpdateDTO {
    @NotNull
    @NotBlank(message = "please input your firstname")
    private String firstName;
    @NotNull
    @NotBlank(message = "please input your lastname")
    private String lastName;
    @NotNull
    @NotBlank(message = "please input your email")
    @Email
    private String email;
    @NotNull
    @NotBlank(message = "Please input the password")
    private String password;
    private String address;
    private String phone;
    private Date createAt;
}
