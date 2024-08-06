package com.metaphorce.product.controller;

import com.metaphorce.commonslib.dto.CreateOrderRequest;
import com.metaphorce.commonslib.dto.CreateOrderResponse;
import com.metaphorce.commonslib.dto.OrderDto;
import com.metaphorce.commonslib.entities.Order;
import com.metaphorce.product.service.OrderService;
import com.metaphorce.product.service.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@Slf4j
@Validated
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/create")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {

        CreateOrderResponse createOrderResponse = orderService.createOrder(request);
        return ResponseEntity.ok(createOrderResponse);
    }

    @GetMapping("/get")
    public ResponseEntity<OrderDto> getOrder(@RequestParam Long orderId) {
        OrderDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }
}
