package com.prueba.sportsshop.backend.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CartItemDto {
    private Integer itemId;
    private Integer productId;
    private String productDescription;
    private String imageUrl;
    private double price;
    private int quantity;
    private double subtotal;
}
