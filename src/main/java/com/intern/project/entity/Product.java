package com.intern.project.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import java.util.Date;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id",columnDefinition = "bigint(20)")
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

}
