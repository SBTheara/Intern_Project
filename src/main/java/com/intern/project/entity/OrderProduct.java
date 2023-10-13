package com.intern.project.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
public class OrderProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
  private long id;

  private String address;
  private String orderDate;
  private String status;
  private String createAt;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "orderProduct")
  private List<OrderItem> orderItems = new ArrayList<>();
}
