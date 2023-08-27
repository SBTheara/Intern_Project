package com.example.mysmallproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="category_id")
    private int id;
    private String name;
    private String details;
//    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//    @JsonIgnore
//    private Set<Products> products = new HashSet<>();
}
