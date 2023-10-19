package com.intern.project.repository;

import com.intern.project.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    void deleteAllByIdIn(List<Long> productIdsList);
}
