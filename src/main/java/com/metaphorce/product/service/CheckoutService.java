package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.ProcessCheckoutRequest;

public interface CheckoutService {

    void processCheckout(ProcessCheckoutRequest request);
}
