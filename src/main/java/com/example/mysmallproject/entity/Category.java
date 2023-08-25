package com.example.mysmallproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private int id;
    private String name;
    private String details;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "FKCategoryProducts",referencedColumnName = "category_id")
    private List<Products> products = new ArrayList<>();
}
