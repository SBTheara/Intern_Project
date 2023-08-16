package com.example.mysmallproject.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_details")
@Data
public class Order_Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_details_id;
    private int order_id;
    private int product_id;
    private int quantity;

}
