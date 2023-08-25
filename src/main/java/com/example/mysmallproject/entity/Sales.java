package com.example.mysmallproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "sales")
@Data
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sale_id;
    private int sales_amount;
    private Date create_at;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;
}
