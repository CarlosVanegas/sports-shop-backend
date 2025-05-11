package com.prueba.sportsshop.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDto {
    private Integer id;
    private String imageUrl;
    private String description;
    private BigDecimal price;
    private Integer quantityAvailable;
    private OffsetDateTime createdAt;
}
