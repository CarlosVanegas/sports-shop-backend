package com.prueba.sportsshop.backend.controller;

import com.prueba.sportsshop.backend.dto.OrderResponseDto;
import com.prueba.sportsshop.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.prueba.sportsshop.backend.dto.OrderSummaryDto;
import org.springframework.web.bind.annotation.*;
import com.prueba.sportsshop.backend.dto.OrderDetailResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> confirmOrder(
            @AuthenticationPrincipal UserDetails user) {

        OrderResponseDto response = orderService.createOrder(user.getUsername());
        return ResponseEntity
                .status(201)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderSummaryDto>> listUserOrders(
            @AuthenticationPrincipal UserDetails user) {
        List<OrderSummaryDto> summaries = orderService.listOrders(user.getUsername());
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponseDto> getOrder(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Integer orderId) {

        OrderDetailResponseDto dto = orderService.getOrderDetails(user.getUsername(), orderId);
        return ResponseEntity.ok(dto);
    }
}
