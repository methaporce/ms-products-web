package com.metaphorce.product.repository;

import com.metaphorce.commonslib.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT c.id, c.users_id, c.active, c.deleted " +
            "FROM cart c " +
            "WHERE c.users_id = :userId AND c.active = true AND c.deleted = false",
            nativeQuery = true)
    Optional<Cart> findByUserIdAndIsActive (Long userId);

}