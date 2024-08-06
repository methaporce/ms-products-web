package com.metaphorce.product.repository;

import com.metaphorce.commonslib.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT o.id, o.total_to_pay, p.id, p.path_image, p.name, p.price, ci.quantity " +
            "FROM orders o " +
            "JOIN cart_item ci ON o.cart_id = ci.cart_id " +
            "JOIN product p ON ci.product_id = p.id " +
            "WHERE o.id = :orderId",
            nativeQuery = true)
    List<Object[]> getProductsByOrderId(@Param("orderId") Long orderId);
}
