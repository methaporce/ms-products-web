package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.CreateOrderRequest;
import com.metaphorce.commonslib.entities.PaymentMethod;
import com.metaphorce.commonslib.entities.Cart;
import com.metaphorce.commonslib.entities.Order;
import com.metaphorce.product.repository.PaymentMethodRepository;
import com.metaphorce.product.repository.CartRepository;
import com.metaphorce.product.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public void createOrder(CreateOrderRequest request) {

        Long cart = cartRepository.findById(request.getCartId()).get().getId();

        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("Payment method not found"));

        /*
        Order order = new Order();
        order.setCart(cart);
        order.setPaymentMethod(paymentMethod);
        orderRepository.save(order);
        */
    }
}
