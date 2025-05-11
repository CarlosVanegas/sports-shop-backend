package com.prueba.sportsshop.backend.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderSummaryDto {
    private Integer orderId;
    private OffsetDateTime orderDate;
    private String status;
    private BigDecimal totalAmount;
}
