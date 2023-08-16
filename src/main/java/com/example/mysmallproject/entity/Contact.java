package com.example.mysmallproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "contact")
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contact_id;
    private String firstname;
    private String lastname;
    private String email;
    private String message;
    private Date create_at;
}
