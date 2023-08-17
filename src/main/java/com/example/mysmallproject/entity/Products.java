package com.example.mysmallproject.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;
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
    @NotBlank(message = "Please enter the name of the products")
    private String name;
    @Max(value = 100,message = "Can not input more than 100 characters")
    private String description;
    @Min(value = 1)
    @NotEmpty
    @NotNull
    private int quantity;
    @NotNull
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(1)
    private double price;
    @NotNull
    private Date CreateAt;
    @NotNull
    @NotBlank
    private String image;
}
