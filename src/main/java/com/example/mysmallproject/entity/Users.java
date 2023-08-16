package com.example.mysmallproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String type;
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
