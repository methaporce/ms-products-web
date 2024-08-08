package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.ProcessCheckoutRequest;
import com.metaphorce.commonslib.dto.ProcessCheckoutResponse;
import com.metaphorce.commonslib.entities.Checkout;

import java.util.List;

public interface CheckoutService {

    void processCheckout(ProcessCheckoutRequest request);

    List<ProcessCheckoutResponse> getOrderCheckout(Long id);
}
