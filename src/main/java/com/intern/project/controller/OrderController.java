package com.intern.project.controller;

import com.intern.project.dto.OrderRequest;
import com.intern.project.dto.OrderResponse;
import com.intern.project.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderResponse> saveOrderProducts(@RequestBody OrderRequest request) {
    return new ResponseEntity<>(orderService.saveOrderProducts(request), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<OrderResponse>> getOrders() {
    return new ResponseEntity<>(orderService.getOrder(), HttpStatus.OK);
  }
}
