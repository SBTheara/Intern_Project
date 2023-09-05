package com.example.mysmallproject.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id",columnDefinition = "bigint(20)")
    private long orderId;
    private String address;
    private String orderDate;
    private String status;
    private String createAt;
}
