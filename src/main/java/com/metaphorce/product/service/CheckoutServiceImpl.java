package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.ProcessCheckoutRequest;
import com.metaphorce.commonslib.entities.*;
import com.metaphorce.product.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public void processCheckout(ProcessCheckoutRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (checkoutRepository.existsByOrder(order)) {
            throw new RuntimeException("Checkout already exists");
        }

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



        cartItemRepository.deleteAll(items);
    }


    @Override
    public Checkout getOrderCheckout(Long id) {

        Optional<Checkout> checkout = checkoutRepository.findById(id);

        if (checkout.isPresent()) {
            return checkout.get();
        } else {
            throw new RuntimeException("Checkout not found");
        }
    }
}
