package com.prueba.sportsshop.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderDetailResponseDto {
    private Integer orderId;
    private String status;
    private List<Item> items;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Item {
        private Integer productId;
        private String description;
        private String imageUrl;
        private Integer quantity;
        private BigDecimal priceAtTime;
        private BigDecimal subtotal;
    }
}
