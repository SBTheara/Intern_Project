package com.intern.project.service;

import com.intern.project.dto.OrderRequest;
import com.intern.project.dto.OrderResponse;
import com.intern.project.entity.OrderItem;
import com.intern.project.entity.OrderProduct;
import com.intern.project.entity.Product;
import com.intern.project.exception.OrderNotFoundException;
import com.intern.project.repository.OrderRepository;
import com.intern.project.exception.ProductNotFoundException;
import com.intern.project.repository.ProductRepository;
import com.intern.project.utils.PageRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final ModelMapper modelMapper;

  public OrderResponse createOrder(OrderRequest request) {
    OrderProduct orderProduct = new OrderProduct();
    List<OrderItem> orderItemList = getOrderItemList(request, orderProduct);
    orderProduct.setOrderItems(orderItemList);
    this.modelMapper.map(request, orderProduct);
    return this.prepareOrderProductResponse(
        orderRepository.save(orderProduct), this.getListProductId(orderProduct));
  }

  private List<OrderItem> getOrderItemList(OrderRequest request, OrderProduct orderProduct) {
    return request.getProductIds().stream()
        .map(productId -> this.prepareOrderItem(productId, orderProduct))
        .toList();
  }

  private OrderResponse prepareOrderProductResponse(
      OrderProduct orderProduct, List<Long> productIds) {
    OrderResponse orderResponse = new OrderResponse();
    orderResponse.setTotalProducts(productIds.size());
    orderResponse.setProductIds(productIds);
    this.modelMapper.map(orderProduct, orderResponse);
    return orderResponse;
  }

  private OrderItem prepareOrderItem(Long productId, OrderProduct orderProduct) {
    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
    OrderItem orderItem = new OrderItem();
    orderItem.setProduct(product);
    orderItem.setOrderProduct(orderProduct);
    return orderItem;
  }

  public Page<OrderResponse> listOrder(PageRequest pageRequest) {
    Pageable pageable = pageRequest.toPageable();
    Page<OrderProduct> orderResponsePage = orderRepository.findAll(pageable);
    List<OrderResponse> listOrderResponse =
        orderResponsePage.getContent().stream()
            .map(orderProduct -> this.modelMapper.map(orderProduct, OrderResponse.class))
            .toList();
    return new PageImpl<>(listOrderResponse, pageable, orderResponsePage.getTotalElements());
  }

  public OrderResponse getOrderById(Long id) {
    OrderProduct orderProduct =
        orderRepository
            .findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));
    return prepareOrderProductResponse(orderProduct, this.getListProductId(orderProduct));
  }

  private List<Long> getListProductId(OrderProduct orderProduct) {
    return orderProduct.getOrderItems().stream()
        .map(orderItem -> orderItem.getProduct().getId())
        .toList();
  }
}
