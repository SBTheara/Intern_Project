package com.intern.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "order_item_seq", sequenceName = "order_item_seq", allocationSize = 1)
public class OrderItem{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "order_product_id")
  private OrderProduct orderProduct;
}
