package com.prueba.sportsshop.backend.service;

import com.prueba.sportsshop.backend.dto.UserRegistrationDto;
import com.prueba.sportsshop.backend.dto.UserUpdateDto;
import com.prueba.sportsshop.backend.entity.User;
import com.prueba.sportsshop.backend.exception.BadRequestException;
import com.prueba.sportsshop.backend.exception.ResourceNotFoundException;
import com.prueba.sportsshop.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(UserRegistrationDto dto) {
        // 1. Validar que password y confirmPassword coincidan
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        // 2. Validar edad >= 18 años
        if (Period.between(dto.getBirthDate(), java.time.LocalDate.now()).getYears() < 18) {
            throw new BadRequestException("User must be at least 18 years old");
        }

        // 3. Validar unicidad de email
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email already in use");
        }

        // 4. Mapear DTO a entidad
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .shippingAddress(dto.getShippingAddress())
                .email(dto.getEmail())
                .birthDate(dto.getBirthDate())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        // 5. Guardar y retornar
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    @Override
    public User updateProfile(String currentEmail, UserUpdateDto dto) {
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Si cambia el email, validar unicidad
        if (!user.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email ya en uso");
        }

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setShippingAddress(dto.getShippingAddress());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());

        return userRepository.save(user);
    }


}
