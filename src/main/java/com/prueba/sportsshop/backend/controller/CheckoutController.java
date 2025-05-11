package com.prueba.sportsshop.backend.controller;

import com.prueba.sportsshop.backend.dto.*;
import com.prueba.sportsshop.backend.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<CheckoutResponseDto> initiate(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody CheckoutRequestDto req) {
        return ResponseEntity.ok(
                checkoutService.initiate(user.getUsername(), req)
        );
    }

    @PostMapping("/{checkoutId}/pay")
    public ResponseEntity<OrderResponseDto> pay(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable String checkoutId,
            @Valid @RequestBody PaymentRequestDto req) {
        return ResponseEntity.status(201).body(
                checkoutService.payAndCreateOrder(user.getUsername(), checkoutId, req)
        );
    }
}
