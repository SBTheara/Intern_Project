package com.example.mysmallproject.request;


import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Data
public class Product_Request {

    @NotNull
    @NotBlank(message = "Please insert the name of the product")
    private String name;
    @NotNull
    @Max(value = 100,message = "The description must be less than 100 alphabets")
    private String description;
    @NotNull
    @Min(value = 0,message = "The quantity must be bigger than 0")
    private int quantity;
    @NotNull
    @NotBlank(message = "Please insert the price of the product")
    private double price;
    @NotNull
    private Date Create_at;
    @Lob
    @NotNull
    @NotBlank
    private byte[] image;
    private String imageFilePath;
    private String imageName;
}
