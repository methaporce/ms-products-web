package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.ProcessCheckoutRequest;
import com.metaphorce.commonslib.entities.Cart;

public interface CheckoutService {

    Cart processCheckout(ProcessCheckoutRequest request);
}
