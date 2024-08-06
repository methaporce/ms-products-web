package com.metaphorce.product.repository;

import com.metaphorce.commonslib.entities.Checkout;
import com.metaphorce.commonslib.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    boolean existsByOrder(Order order);
}
