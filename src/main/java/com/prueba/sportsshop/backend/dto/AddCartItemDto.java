package com.prueba.sportsshop.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AddCartItemDto {
    @NotNull
    private Integer productId;

    @NotNull @Min(1)
    private Integer quantity;
}
