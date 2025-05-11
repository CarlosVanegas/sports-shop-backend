package com.prueba.sportsshop.backend.dto;
import lombok.*;
import jakarta.validation.constraints.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentRequestDto {
    @NotBlank private String cardNumber;
    @NotBlank private String cardExpiry;
    @NotBlank private String cardCvv;
}