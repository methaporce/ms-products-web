package com.metaphorce.product.repository;


import com.metaphorce.commonslib.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<List<Card>> findByUserId(Long userId);
}
