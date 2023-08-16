package com.example.mysmallproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Table(name = "orders")
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_id;
    private int user_id;
    private String shipping_address;
    private Date shipping_date;
    private String shipping_status;
    private Date create_at;
}
