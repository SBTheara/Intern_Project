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
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", insertable=false, updatable=false)
    private Orders orders;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="product_id", insertable=false, updatable=false)
    private Products products;
}
