package com.prueba.sportsshop.backend.repository;

import com.prueba.sportsshop.backend.entity.CheckoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CheckoutSessionRepository extends JpaRepository<CheckoutSession, String> { }
