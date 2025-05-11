package com.prueba.sportsshop.backend.service;

import com.prueba.sportsshop.backend.dto.UserRegistrationDto;
import com.prueba.sportsshop.backend.dto.UserUpdateDto;
import com.prueba.sportsshop.backend.entity.User;

public interface UserService {
    /**
     * Registra un nuevo usuario.
     * @param dto datos de registro
     * @return la entidad User creada
     */
    User register(UserRegistrationDto dto);
    User findByEmail(String email);
    User updateProfile(String currentEmail, UserUpdateDto dto);
}
