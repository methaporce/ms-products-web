package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.CreateOrderRequest;
import com.metaphorce.commonslib.dto.CreateOrderResponse;
import com.metaphorce.commonslib.dto.OrderDto;
import com.metaphorce.commonslib.entities.Order;

import java.util.List;

public interface OrderService {

    CreateOrderResponse createOrder(CreateOrderRequest request);

    OrderDto getOrder(Long orderId);
}
