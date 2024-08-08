package com.metaphorce.product.controller;

import com.metaphorce.product.service.CheckoutTraceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/checkout/trace")
@Slf4j
@Validated
public class CheckoutTraceController {

    @Autowired
    private CheckoutTraceServiceImpl checkoutTraceService;

    @PostMapping("/process")
    public ResponseEntity processCheckout(@RequestParam Long orderId, @RequestParam String status) {

        checkoutTraceService.processCheckoutTrace(orderId, status);
        return ResponseEntity.accepted().build();
    }

}
