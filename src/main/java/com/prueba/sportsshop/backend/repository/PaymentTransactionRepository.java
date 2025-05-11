package com.prueba.sportsshop.backend.repository;

import com.prueba.sportsshop.backend.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Integer> { }