package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.ProcessCheckoutRequest;
import com.metaphorce.commonslib.entities.Checkout;

public interface CheckoutService {

    void processCheckout(ProcessCheckoutRequest request);

    Checkout getOrderCheckout(Long id);
}
