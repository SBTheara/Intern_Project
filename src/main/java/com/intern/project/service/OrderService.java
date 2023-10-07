package com.intern.project.service;

import com.intern.project.dto.OrderRequest;
import com.intern.project.dto.OrderResponse;
import com.intern.project.entity.OrderProduct;
import com.intern.project.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final ModelMapper modelMapper;

  public OrderResponse saveOrderProducts(OrderRequest request) {
    OrderProduct orderProduct = this.modelMapper.map(request, OrderProduct.class);
    return this.modelMapper.map(orderRepository.save(orderProduct), OrderResponse.class);
  }

  public List<OrderResponse> getOrder() {

    return orderRepository.findAll().stream().map(this::prepareOrderResponse).toList();
  }

  private OrderResponse prepareOrderResponse(OrderProduct orderProduct) {
    OrderResponse orderResponse = new OrderResponse();
    this.modelMapper.map(orderProduct, orderResponse);
    return orderResponse;
  }
}
