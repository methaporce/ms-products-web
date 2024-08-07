package com.metaphorce.product.repository;


import com.metaphorce.commonslib.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
