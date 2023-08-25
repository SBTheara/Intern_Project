package com.example.mysmallproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
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
    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Orders> orders = new ArrayList<>();
}
