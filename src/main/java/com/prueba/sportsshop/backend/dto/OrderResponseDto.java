package com.prueba.sportsshop.backend.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderResponseDto {
    private Integer orderId;
    private String message;
}
