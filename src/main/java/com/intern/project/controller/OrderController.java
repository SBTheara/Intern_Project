package com.intern.project.controller;

import com.intern.project.dto.OrderRequest;
import com.intern.project.dto.OrderResponse;
import com.intern.project.dto.PageResponse;
import com.intern.project.dto.ProductIdsRequest;
import com.intern.project.service.OrderService;
import com.intern.project.utils.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
    return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<PageResponse<OrderResponse>> getOrderProduct(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "15") int size,
      @RequestParam(defaultValue = "desc") String direction,
      @RequestParam(defaultValue = "id",required = false) String sortBy) {
    PageRequest pageRequest =
        PageRequest.builder().page(page - 1).size(size).direction(direction).sort(sortBy).build();
    Page<OrderResponse> orderResponsesPage = orderService.listOrder(pageRequest);
    return new ResponseEntity<>(
        new PageResponse<>(
            orderResponsesPage.getSize(),
            orderResponsesPage.getNumber(),
            orderResponsesPage.getTotalElements(),
            orderResponsesPage.getContent()),
        HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id){
    return new ResponseEntity<>(orderService.getOrderById(id),HttpStatus.OK);
  }

  @DeleteMapping("/{id}/products")
  public ResponseEntity<Void> deleteProductFromOrder(@PathVariable Long id, @RequestBody ProductIdsRequest productIdsRequest){
    this.orderService.deleteProductsFromOrder(id,productIdsRequest);
    return ResponseEntity.noContent().build();
  }
}
