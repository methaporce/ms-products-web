package com.metaphorce.product.controller;

import com.metaphorce.commonslib.dto.ProcessCheckoutRequest;
import com.metaphorce.commonslib.dto.ProcessCheckoutResponse;
import com.metaphorce.commonslib.entities.Cart;
import com.metaphorce.commonslib.entities.Checkout;
import com.metaphorce.product.service.CheckoutServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/checkout")
@Slf4j
@Validated
public class CheckoutController {

    @Autowired
    private CheckoutServiceImpl checkoutService;

    @PostMapping("/process")
    public ResponseEntity processCheckout(@RequestBody ProcessCheckoutRequest request) {


        checkoutService.processCheckout(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/getOrderCheckout")
    public ResponseEntity< List<ProcessCheckoutResponse>> getOrderCheckout(@RequestParam Long id) {

        List<ProcessCheckoutResponse> checkout = checkoutService.getOrderCheckout(id);

        return ResponseEntity.ok(checkout);
    }


}
