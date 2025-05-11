package com.prueba.sportsshop.backend.service;

import com.prueba.sportsshop.backend.dto.AddCartItemDto;
import com.prueba.sportsshop.backend.dto.CartItemDto;

import java.util.List;

public interface CartService {
    List<CartItemDto> listCartItems(String userEmail);
    CartItemDto addItem(String userEmail, AddCartItemDto dto);
    void removeItem(String userEmail, Integer itemId);
}
