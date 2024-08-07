package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.ProcessCheckoutRequest;
import com.metaphorce.commonslib.dto.ProcessCheckoutResponse;
import com.metaphorce.commonslib.dto.ProductDto;
import com.metaphorce.commonslib.entities.*;
import com.metaphorce.product.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        checkout.setStatus(Checkout.CheckoutStatusEnum.IN_PROGRESS);
        checkout.setDate(LocalDateTime.now());
        checkoutRepository.save(checkout);

    }


    @Override
    public ProcessCheckoutResponse getOrderCheckout(Long id) {

        Optional<Checkout> checkout = checkoutRepository.findById(id);

        if (checkout.isPresent()) {
            ProcessCheckoutResponse checkoutResponse = new ProcessCheckoutResponse();

            checkoutResponse.setTotalToPay(checkout.get().getTotalToPay());
            checkoutResponse.setDate(checkout.get().getDate());
            checkoutResponse.setIdCheckout(checkout.get().getId());
            checkoutResponse.setCheckoutStatus(String.valueOf(checkout.get().getStatus()));
            checkoutResponse.setOrderId(checkout.get().getOrder().getId());
            checkoutResponse.setCartId(checkout.get().getOrder().getCart().getId());
            checkoutResponse.setUserId(checkout.get().getOrder().getCart().getUser().getId());
            checkoutResponse.setProducts(checkout.get().getOrder().getCart().getItems().stream().map(cartItem -> {
                ProductDto productDto = new ProductDto();
                productDto.setProductId(cartItem.getProduct().getId());
                productDto.setProductPathImage(cartItem.getProduct().getPathImage());
                productDto.setProductName(cartItem.getProduct().getName());
                productDto.setProductPrice(cartItem.getProduct().getPrice());
                productDto.setProductQuantity(cartItem.getQuantity());

                return productDto;
            }).toList());

            return checkoutResponse;
        } else {
            throw new RuntimeException("Checkout not found");
        }
    }
}
