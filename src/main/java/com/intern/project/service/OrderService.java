package com.intern.project.service;

import com.intern.project.dto.OrderRequest;
import com.intern.project.dto.OrderResponse;
import com.intern.project.dto.ProductIdsRequest;
import com.intern.project.entity.OrderItem;
import com.intern.project.entity.OrderProduct;
import com.intern.project.entity.Product;
import com.intern.project.exception.OrderNotFoundException;
import com.intern.project.exception.ProductBadRequesException;
import com.intern.project.repository.OrderItemRepository;
import com.intern.project.repository.OrderRepository;
import com.intern.project.repository.ProductRepository;
import com.intern.project.utils.PageRequest;
import java.util.ArrayList;
import java.util.List;

import com.intern.project.utils.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final ProductRepository productRepository;
  private final ModelMapper modelMapper;

  public OrderResponse createOrder(OrderRequest request) {
    OrderProduct orderProduct = new OrderProduct();
    List<Product> productList = productRepository.findAll(ProductSpecification.getActiveProducts());
    List<Long> productIdsList = productList.stream().map(Product::getId).toList();
    this.validateProductIds(request.getProductIds(),productIdsList);
    List<OrderItem> orderItemList = getOrderItemList(productList, orderProduct);
    orderProduct.setOrderItems(orderItemList);
    this.modelMapper.map(request, orderProduct);
    return this.prepareOrderProductResponse(
        orderRepository.save(orderProduct),productIdsList);
  }

  private void validateProductIds(List<Long> productIds, List<Long> productIdsList) {
    List<Long> productListReq = new ArrayList<>(productIds);
    List<Long> productList = new ArrayList<>(productIdsList);
    productListReq.removeAll(productList);
    if(!productListReq.isEmpty()){
      throw new ProductBadRequesException("Not found for "+productListReq);
    }
  }

  private List<OrderItem> getOrderItemList(List<Product> productList, OrderProduct orderProduct) {
    return productList.stream()
        .map(product -> this.prepareOrderItem(product, orderProduct))
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

  private OrderItem prepareOrderItem(Product product, OrderProduct orderProduct) {
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
        orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    return prepareOrderProductResponse(orderProduct, this.getListProductId(id));
  }

  private List<Long> getListProductId(Long id) {
    return productRepository.findAll(ProductSpecification.getActiveProduct(id)).stream()
        .map(Product::getId)
        .toList();
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteProductsFromOrder(Long id, ProductIdsRequest productIdsRequest) {
    OrderProduct orderProduct =
        orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    List<OrderItem> orderItemList = this.prepareForOrderItemIds(orderProduct, productIdsRequest);
    List<Long> orderItemIdsList = orderItemList.stream().map(OrderItem::getId).toList();
    this.validateProductIds(productIdsRequest.getProductIds(), orderProduct);
    orderItemRepository.deleteAllByIdInBatch(orderItemIdsList);
  }

  private void validateProductIds(List<Long> productIdsRequest, OrderProduct orderProduct) {
    List<Long> productIdsReq = new ArrayList<>(productIdsRequest);
    List<Long> productIdsList =
        orderProduct.getOrderItems().stream()
            .map(orderItem -> orderItem.getProduct().getId())
            .toList();
    productIdsReq.removeAll(productIdsList);
    if (!productIdsReq.isEmpty()) {
      throw new ProductBadRequesException("Not found for product has ids : " + productIdsReq);
    }
  }

  private List<OrderItem> prepareForOrderItemIds(
      OrderProduct orderProduct, ProductIdsRequest productIdsRequest) {
    return orderProduct.getOrderItems().stream()
        .filter(
            orderItem -> productIdsRequest.getProductIds().contains(orderItem.getProduct().getId()))
        .toList();
  }
}
