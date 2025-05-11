package com.prueba.sportsshop.backend.controller;

import com.prueba.sportsshop.backend.dto.LoginRequestDto;
import com.prueba.sportsshop.backend.dto.LoginResponseDto;
import com.prueba.sportsshop.backend.dto.UserRegistrationDto;
import com.prueba.sportsshop.backend.dto.UserResponseDto;
import com.prueba.sportsshop.backend.entity.User;
import com.prueba.sportsshop.backend.service.UserService;
import com.prueba.sportsshop.backend.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequestDto dto) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );

            String token = jwtUtil.generateToken(auth.getName());
            return ResponseEntity.ok(LoginResponseDto.builder()
                    .token(token)
                    .tokenType("Bearer")
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email o password incorrectos"));
        }
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(
            @Valid @RequestBody UserRegistrationDto registrationDto) {

        User saved = userService.register(registrationDto);

        UserResponseDto response = UserResponseDto.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .shippingAddress(saved.getShippingAddress())
                .email(saved.getEmail())
                .birthDate(saved.getBirthDate())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
