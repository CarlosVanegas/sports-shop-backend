package com.prueba.sportsshop.backend.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String token;
    @Builder.Default
    private String tokenType = "Bearer";
}
