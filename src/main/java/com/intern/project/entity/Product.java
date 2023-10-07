package com.intern.project.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "product_seq",sequenceName = "product_seq",allocationSize = 1)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_seq")
    private long id;
    @NotNull
    @NotBlank(message = "Please enter the name of the products")
    private String name;
    @Size(max = 100,message = "Description allow only 100 characters")
    private String description;
    private int quantity;
    private double price;
    @NotNull
    private Date createAt;
    @NotNull
    @NotBlank
    private String image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private OrderProduct orderProduct;

}
