package com.metaphorce.product.controller;


import com.metaphorce.commonslib.dto.AddToCartRequest;
import com.metaphorce.commonslib.dto.AddToCartResponse;
import com.metaphorce.commonslib.dto.CartItemDto;
import com.metaphorce.commonslib.entities.Cart;
import com.metaphorce.commonslib.entities.CartItem;
import com.metaphorce.product.service.CartServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @PostMapping("/add")
    public ResponseEntity<AddToCartResponse> addToCart(@RequestBody AddToCartRequest request) {

        log.info("Adding product to cart: {}", request);
        AddToCartResponse cart = cartService.addToCart(request);

        return ResponseEntity.ok(cart);
    }

    @GetMapping("/getCartByCartId")
    public ResponseEntity<List<CartItemDto>> getCart(@RequestParam Long cartId) {
        List<CartItemDto> cartItems = cartService.getCartItems(cartId);
        return ResponseEntity.ok(cartItems);
    }
}
