package com.metaphorce.product.repository;

import com.metaphorce.commonslib.dto.CartItemDto;
import com.metaphorce.commonslib.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.annotation.Native;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    @Query(value = "SELECT p.id, p.path_image, p.name, p.price, ci.quantity " +
            "FROM cart_item ci " +
            "JOIN product p ON ci.product_id = p.id " +
            "WHERE ci.cart_id = :cartId",
            nativeQuery = true)
    List<Object[]> getCartItems(@Param("cartId") Long cartId);

}