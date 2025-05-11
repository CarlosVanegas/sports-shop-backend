package com.prueba.sportsshop.backend.controller;

import com.prueba.sportsshop.backend.dto.UserResponseDto;
import com.prueba.sportsshop.backend.entity.User;
import com.prueba.sportsshop.backend.service.UserService;
import com.prueba.sportsshop.backend.dto.UserUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getProfile(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findByEmail(userDetails.getUsername());
        UserResponseDto dto = UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .shippingAddress(user.getShippingAddress())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserUpdateDto dto) {

        User updated = userService.updateProfile(userDetails.getUsername(), dto);

        UserResponseDto response = UserResponseDto.builder()
                .id(updated.getId())
                .firstName(updated.getFirstName())
                .lastName(updated.getLastName())
                .shippingAddress(updated.getShippingAddress())
                .email(updated.getEmail())
                .birthDate(updated.getBirthDate())
                .createdAt(updated.getCreatedAt())
                .updatedAt(updated.getUpdatedAt())
                .build();

        return ResponseEntity.ok(response);
    }
}
