package com.prueba.sportsshop.backend.dto;

import lombok.*;
import java.time.OffsetDateTime;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private String email;
    private LocalDate birthDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
