package com.intern.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
  private long id;

  @NotNull
  @NotBlank(message = "Please enter the name of the products")
  private String name;

  @Size(max = 100, message = "Description allow only 100 characters")
  private String description;

  private int quantity;
  private double price;
  @NotNull private Date createAt;
  @NotNull @NotBlank private String image;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
  private List<OrderItem> orderItem = new ArrayList<>();
}
