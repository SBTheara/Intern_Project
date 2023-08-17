package com.example.mysmallproject.entity;

import com.fasterxml.jackson.databind.DatabindException;
import jakarta.persistence.*;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull
    private String name;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @Size(min = 1,message = "must be equal or more than one")
    private int quantity;
    @NotNull
    private double price;
    @NotNull
    private Date Create_at;
    @Lob
    @NotNull
    private byte[] image;
    private String imageFilePath;
    private String imageName;
}
