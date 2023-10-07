package com.intern.project.repository;

import com.intern.project.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderProduct,Long>, JpaSpecificationExecutor<OrderProduct> {

}
