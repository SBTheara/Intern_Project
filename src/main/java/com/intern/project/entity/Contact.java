package com.intern.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "contact")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id",columnDefinition = "bigint(20)")
    private Long contactId;
    private String firstName;
    private String lastName;
    private String email;
    private String message;
    private Date createAt;
}
