package com.prueba.sportsshop.backend.controller;

import com.prueba.sportsshop.backend.dto.AddCartItemDto;
import com.prueba.sportsshop.backend.dto.CartItemDto;
import com.prueba.sportsshop.backend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartItemDto>> list(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(cartService.listCartItems(user.getUsername()));
    }

    @PostMapping("/items")
    public ResponseEntity<CartItemDto> addItem(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody AddCartItemDto dto) {
        CartItemDto added = cartService.addItem(user.getUsername(), dto);
        return ResponseEntity.ok(added);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Integer itemId) {
        cartService.removeItem(user.getUsername(), itemId);
        return ResponseEntity.noContent().build();
    }
}
