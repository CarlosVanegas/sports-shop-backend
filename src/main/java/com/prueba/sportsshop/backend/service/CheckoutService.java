package com.prueba.sportsshop.backend.service;

import com.prueba.sportsshop.backend.dto.*;
import java.security.Principal;

public interface CheckoutService {
    CheckoutResponseDto initiate(String userEmail, CheckoutRequestDto req);
    OrderResponseDto payAndCreateOrder(String userEmail, String sessionId, PaymentRequestDto req);
}
