package com.metaphorce.product.service;

import com.metaphorce.commonslib.entities.Checkout;
import com.metaphorce.commonslib.entities.CheckoutTrace;
import com.metaphorce.commonslib.entities.Order;
import com.metaphorce.commonslib.entities.Product;
import com.metaphorce.product.controller.CheckoutTraceController;
import com.metaphorce.product.repository.CheckoutRepository;
import com.metaphorce.product.repository.CheckoutTraceRepository;
import com.metaphorce.product.repository.OrderRepository;
import com.metaphorce.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class CheckoutTraceServiceImpl implements CheckoutTraceService {

    @Autowired
    private CheckoutTraceRepository checkoutTraceRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void processCheckoutTrace(Long id, String status) {

        Checkout checkout = checkoutRepository.findById(id).get();

        checkout.setStatus(Checkout.CheckoutStatusEnum.valueOf(status));

        CheckoutTrace checkoutTrace = new CheckoutTrace();
        Order order = checkout.getOrder();
        // Product product = order.getCart().getItems().get(0).getProduct();

        if (status.equals("COMPLETED")) {
            checkoutTrace.setDate(LocalDateTime.now());
            checkoutTrace.setMessage("[ Order completed successfully: -> status " + Checkout.CheckoutStatusEnum.COMPLETED + " ]");
            checkoutTrace.setCheckout(checkout);
            checkoutRepository.save(checkout);
            checkoutTraceRepository.save(checkoutTrace);

            order.setOrderStatus(Order.OrderStatusEnum.ACCEPTED);
            orderRepository.save(order);

        } else if (status.equals("REJECTED")) {
            checkoutTrace.setMessage("[ Order was rejected because of invalid error: -> status " + Checkout.CheckoutStatusEnum.REJECTED + " ]");
            checkoutTrace.setDate(LocalDateTime.now());
            checkoutTrace.setCheckout(checkout);
            checkoutRepository.save(checkout);
            checkoutTraceRepository.save(checkoutTrace);

            order.setOrderStatus(Order.OrderStatusEnum.CANCELLED);
            orderRepository.save(order);
            // TO DO: add product back to stock
            // and notify user that order was rejected

        } else if (status.equals("CANCELLED")) {
            checkoutTrace.setMessage("[ Order was cancelled because of payment error: -> status " + Checkout.CheckoutStatusEnum.CANCELLED + " ]");
            checkoutTrace.setDate(LocalDateTime.now());
            checkoutTrace.setCheckout(checkout);
            checkoutRepository.save(checkout);
            checkoutTraceRepository.save(checkoutTrace);

            order.setOrderStatus(Order.OrderStatusEnum.ERROR);
            orderRepository.save(order);

            // TO DO: add product back to stock
            // and notify user that order was cancelled

        } else if (status.equals("EXPIRED")) {
            checkoutTrace.setMessage("[ Order expired because of waiting time in payment response: -> status " + Checkout.CheckoutStatusEnum.EXPIRED + " ]");
            checkoutTrace.setDate(LocalDateTime.now());
            checkoutTrace.setCheckout(checkout);
            checkoutRepository.save(checkout);
            checkoutTraceRepository.save(checkoutTrace);

            order.setOrderStatus(Order.OrderStatusEnum.EXPIRED);
            orderRepository.save(order);

            // TO DO: add product back to stock
            // and notify user that order was expired

        } else {
            checkoutTrace.setMessage("[ Order was not completed because of invalid status: -> status " + Checkout.CheckoutStatusEnum.UNKNOWN + " ]");
            checkoutTrace.setDate(LocalDateTime.now());
            checkoutTrace.setCheckout(checkout);
            checkoutRepository.save(checkout);
            checkoutTraceRepository.save(checkoutTrace);

            order.setOrderStatus(Order.OrderStatusEnum.UNKNOWN);
            orderRepository.save(order);

            // TO DO: add product back to stock
            // and notify user that order was in unknown status

        }








    }
}
