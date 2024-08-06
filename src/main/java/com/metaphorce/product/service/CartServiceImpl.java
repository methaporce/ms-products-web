package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.AddToCartRequest;
import com.metaphorce.commonslib.dto.AddToCartResponse;
import com.metaphorce.commonslib.dto.CartItemDto;
import com.metaphorce.commonslib.entities.Cart;
import com.metaphorce.commonslib.entities.CartItem;
import com.metaphorce.commonslib.entities.Product;
import com.metaphorce.commonslib.entities.Users;
import com.metaphorce.product.mapper.GlobalMapper;
import com.metaphorce.product.repository.CartItemRepository;
import com.metaphorce.product.repository.ProductRepository;
import com.metaphorce.product.repository.CartRepository;
import com.metaphorce.product.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AddToCartResponse addToCart(AddToCartRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        Cart cart = cartRepository.findByUserId(request.getUserId())

                .orElseGet(() -> {

                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);

                });

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), request.getProductId())
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        cartItemRepository.save(cartItem);

        AddToCartResponse addToCartResponse = new AddToCartResponse();

        addToCartResponse.setCartId(cart.getId());
        addToCartResponse.setUserId(cart.getUser().getId());
        addToCartResponse.setCategoryId(product.getCategory().getId());
        addToCartResponse.setCategoryName(product.getCategory().getName());
        addToCartResponse.setProductId(product.getId());
        addToCartResponse.setProductName(product.getName());
        addToCartResponse.setProductPrice(product.getPrice());
        addToCartResponse.setProductTotal(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        addToCartResponse.setProductImage(product.getPathImage());
        addToCartResponse.setProductStock(product.getStock());
        addToCartResponse.setQuantity(cartItem.getQuantity());

        return addToCartResponse;
    }

    @Override
    public List<CartItemDto> getCartItems(Long cardId) {

        List<Object[]> rawItems = cartItemRepository.getCartItems(cardId);

        return rawItems.stream()
                .map(item -> GlobalMapper.INSTANCE.toCartItemDto(
                        (Long) item[0],
                        (String) item[1],
                        (String) item[2],
                        (BigDecimal) item[3],
                        (Integer) item[4]
                ))
                .collect(Collectors.toList());

    }

}
