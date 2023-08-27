package com.example.mysmallproject.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Validated
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private int id;
    @NotNull
    @NotBlank(message = "Please enter the name of the products")
    private String name;
    @Size(max = 100,message = "Description allow only 100 characters")
    private String description;
    private int quantity;
    private double price;
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date CreateAt;
    @NotNull
    @NotBlank
    private String image;
//    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//    @JoinTable(name = "product_category",
//            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"))
//    @Size(min = 1)
//    @NotNull
//    private Set<Category> categories = new HashSet<>();
}
