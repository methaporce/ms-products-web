package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.CreateOrderRequest;

public interface OrderService {

    void createOrder(CreateOrderRequest request);
}
