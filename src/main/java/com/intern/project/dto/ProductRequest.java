package com.intern.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull
    @NotBlank(message = "Please enter the name of the products")
    private String name;
    @Size(max = 100, message = "Description allow only 100 characters")
    private String description;
    private int quantity;
    private double price;
    private boolean isActive;
    @NotNull
    @NotBlank
    private String image;
}
