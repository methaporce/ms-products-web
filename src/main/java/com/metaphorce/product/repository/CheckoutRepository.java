package com.metaphorce.product.repository;

import com.metaphorce.commonslib.entities.Checkout;
import com.metaphorce.commonslib.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    boolean existsByOrder(Order order);

    Optional<Checkout> findById(Long id);

}
