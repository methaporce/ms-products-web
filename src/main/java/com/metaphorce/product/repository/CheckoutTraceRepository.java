package com.metaphorce.product.repository;

import com.metaphorce.commonslib.entities.CheckoutTrace;
import com.metaphorce.product.controller.CheckoutTraceController;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutTraceRepository extends JpaRepository<CheckoutTrace, Long> {
}
