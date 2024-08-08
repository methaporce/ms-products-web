package com.metaphorce.product.service;

import com.metaphorce.product.controller.CheckoutTraceController;

public interface CheckoutTraceService {

    void processCheckoutTrace(Long id, String status);
}
