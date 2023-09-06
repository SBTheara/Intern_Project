package com.intern.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",columnDefinition = "bigint(20)")
    private long id;
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
    @NotNull
    @NotBlank(message = "Please input the address")
    private String address;
    @NotNull
    @NotBlank(message = "Please input your phone number")
    private String phone;
    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date createAt;
}
