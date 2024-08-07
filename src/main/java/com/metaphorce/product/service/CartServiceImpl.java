package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.AddToCartRequest;
import com.metaphorce.commonslib.dto.AddToCartResponse;
import com.metaphorce.commonslib.dto.CartItemDto;
import com.metaphorce.commonslib.entities.*;
import com.metaphorce.product.mapper.GlobalMapper;
import com.metaphorce.product.repository.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public AddToCartResponse addToCart(AddToCartRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        Optional<Cart> cartUserOpt = cartRepository.findByUserIdAndIsActive(user.getId());

        Cart cart ;
        if (cartUserOpt.isPresent() && cartUserOpt.get().getActive()) {

            cart = cartUserOpt.get();

            Optional<Order> orderOpt = orderRepository.findByCartId(cart.getId());

            if (orderOpt.isPresent()) {

                // Si hay una orden, no crear uno nuevo
                cart = new Cart();
                cart.setUser(user);
                cart.setActive(true);
                cart.setDeleted(false);
                cartRepository.save(cart);
            }
        } else {
            // Si no hay carrito, crear uno nuevo
            cart = new Cart();
            cart.setUser(user);
            cart.setActive(true);
            cart.setDeleted(false);
            cartRepository.save(cart);
        }

        Cart finalCart = cart;

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(finalCart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        cartItemRepository.save(cartItem);

        AddToCartResponse addToCartResponse = new AddToCartResponse();
        addToCartResponse.setCartId(cart.getId());
        addToCartResponse.setUserId(user.getId());
        addToCartResponse.setCategoryId(product.getCategory().getId());
        addToCartResponse.setCategoryName(product.getCategory().getName());
        addToCartResponse.setProductId(product.getId());
        addToCartResponse.setProductName(product.getName());
        addToCartResponse.setProductPrice(product.getPrice());
        addToCartResponse.setProductTotal(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        addToCartResponse.setProductImage(product.getPathImage());
        addToCartResponse.setProductStock(product.getStock());
        addToCartResponse.setQuantity(request.getQuantity());


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
