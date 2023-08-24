package com.example.mysmallproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_ID;
    @NotNull
    @NotBlank(message = "please input your firstname")
    private String firstname;
    @NotNull
    @NotBlank(message = "please input your lastname")
    private String lastname;
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
    @NotBlank(message = "Please input your phone nymber")
    private String phone;
    @NotNull
    @NotBlank(message = "Please input the your type")
    private String type;
    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date create_at;

    public Users(String firstname, String lastname, String email, String password, String address, String phone, String type, Date create_at) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.type = type;
        this.create_at = create_at;
    }
}
