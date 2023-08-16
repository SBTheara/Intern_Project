package com.example.mysmallproject.entity;

import com.fasterxml.jackson.databind.DatabindException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int product_id;
    private String name;
    private String description;
    private int quantity;
    private double price;
    private Date Create_at;
    @Lob
    private byte[] image;
    private String imageFilePath;
    private String imageName;
}
