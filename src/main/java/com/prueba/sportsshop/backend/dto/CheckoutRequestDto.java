package com.prueba.sportsshop.backend.dto;
import lombok.*;
import jakarta.validation.constraints.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CheckoutRequestDto {
    @NotBlank private String billingAddress;
    @NotBlank private String paymentMethod;
}