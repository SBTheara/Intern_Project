package com.intern.project.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private double price;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createAt;
    private String image;
}
