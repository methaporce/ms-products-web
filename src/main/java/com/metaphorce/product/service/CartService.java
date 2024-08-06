package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.AddToCartRequest;
import com.metaphorce.commonslib.dto.AddToCartResponse;
import com.metaphorce.commonslib.dto.CartItemDto;
import com.metaphorce.commonslib.entities.Cart;
import com.metaphorce.commonslib.entities.CartItem;

import java.util.List;

public interface CartService {

    AddToCartResponse addToCart(AddToCartRequest request);

    List<CartItemDto> getCartItems(Long cardId);

}
