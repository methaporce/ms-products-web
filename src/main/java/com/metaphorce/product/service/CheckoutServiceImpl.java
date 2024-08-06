package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.ProcessCheckoutRequest;
import com.metaphorce.commonslib.entities.*;
import com.metaphorce.product.repository.CartItemRepository;
import com.metaphorce.product.repository.ProductRepository;
import com.metaphorce.product.repository.CheckoutRepository;
import com.metaphorce.product.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public void processCheckout(ProcessCheckoutRequest request) {

        /*

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Cart cart = order.getCart();

        CartItem cartItem = cartItemRepository.findById(cart.getId())
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Product product = cartItem.getProduct();

        // cart item tiene una lista de productos y cantidades

        for (CartItem item : cart.getItems()) {

            if (item.getQuantity() > product.getStock()) {
                throw new RuntimeException("Insufficient stock");
            }

        }



        product.setStock(product.getStock() - cart.getQuantity());
        productRepository.save(product);

        Checkout checkout = new Checkout();
        checkout.setOrder(order);
        checkout.setTotalToPay(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
        checkout.setStatus(Checkout.StatusEnum.COMPLETED); // TODO change to enum
        checkout.setDate(LocalDateTime.now());

        checkoutRepository.save(checkout);

         */


    }
}
