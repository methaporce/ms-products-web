package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.CreateOrderRequest;
import com.metaphorce.commonslib.dto.CreateOrderResponse;
import com.metaphorce.commonslib.dto.OrderDto;
import com.metaphorce.commonslib.dto.ProductDto;
import com.metaphorce.commonslib.entities.*;
import com.metaphorce.product.mapper.GlobalMapper;
import com.metaphorce.product.repository.CartItemRepository;
import com.metaphorce.product.repository.PaymentMethodRepository;
import com.metaphorce.product.repository.CartRepository;
import com.metaphorce.product.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;


    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        if (!cartRepository.existsById(request.getCartId())) {
            throw new RuntimeException("Cart not found");
        }

        Cart cart = cartRepository.findById(request.getCartId()).get();

        List<CartItem> items = cart.getItems();

        Order order = new Order();

        cart.setDeleted(true);
        cart.setActive(false);

        cartRepository.save(cart);

        order.setCart(cart);

        order.setTotalToPay(cart.getItems().stream()

                .map(e ->
                        e.getProduct()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(e.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        order.setDate(LocalDateTime.now());
        Users user = cart.getUser();
        order.setUsers(user);
        order.setOrderStatus(Order.OrderStatusEnum.INITIAL);
        orderRepository.save(order);

        Long orderId = order.getId();
        CreateOrderResponse createOrderResponse = new CreateOrderResponse();
        createOrderResponse.setOrderId(orderId);
        createOrderResponse.setTotalToPay(order.getTotalToPay());
        createOrderResponse.setCartId(request.getCartId());

        return createOrderResponse;
    }


    @Override
    public OrderDto getOrder(Long orderId) {
        List<Object[]> rawOrder = orderRepository.getProductsByOrderId(orderId);

        OrderDto order = new OrderDto();
        order.setOrderId((Long) rawOrder.get(0)[0]);
        order.setOrderTotalToPay((BigDecimal) rawOrder.get(0)[1]);
        order.setProducts( rawOrder.stream().map(e -> new ProductDto((Long)e[2], (String)e[3], (String)e[4], (BigDecimal)e[5], (Integer)e[6])).collect(Collectors.toList()));

        return order;
    }
}
