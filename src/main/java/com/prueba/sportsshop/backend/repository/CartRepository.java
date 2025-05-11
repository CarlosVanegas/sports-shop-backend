package com.prueba.sportsshop.backend.repository;

import com.prueba.sportsshop.backend.entity.Cart;
import com.prueba.sportsshop.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUser(User user);
}
