package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.ProcessCheckoutRequest;
import com.metaphorce.commonslib.entities.*;
import com.metaphorce.product.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private CartRepository cartRepository;


    @Override
    public Cart processCheckout(ProcessCheckoutRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Cart cart = order.getCart();

        List<CartItem> items = cart.getItems();

        for (CartItem item : items) {

            if (item.getQuantity() > item.getProduct().getStock()) {
                throw new RuntimeException("Insufficient stock");
            }

            item.getProduct().setStock(item.getProduct().getStock() - item.getQuantity());
            productRepository.save(item.getProduct());

        }

        Checkout checkout = new Checkout();
        checkout.setOrder(order);
        checkout.setTotalToPay(order.getTotalToPay());
        checkout.setStatus(Checkout.StatusEnum.IN_PROGRESS);
        checkout.setDate(LocalDateTime.now());
        checkoutRepository.save(checkout);

        if (order.getId().equals(checkout.getOrder().getId())) {
            throw new RuntimeException("Order in progress");
        }

        cartItemRepository.deleteAll(items);

        return cart;
    }
}
