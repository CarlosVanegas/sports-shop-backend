package com.prueba.sportsshop.backend.service;

import com.prueba.sportsshop.backend.dto.*;
import com.prueba.sportsshop.backend.entity.*;
import com.prueba.sportsshop.backend.exception.BadRequestException;
import com.prueba.sportsshop.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final UserService userService;
    private final CartRepository cartRepo;
    private final CheckoutSessionRepository sessionRepo;
    private final PaymentTransactionRepository txRepo;
    private final OrderService orderService;

    @Override
    @Transactional
    public CheckoutResponseDto initiate(String userEmail, CheckoutRequestDto req) {
        User user = userService.findByEmail(userEmail);
        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new BadRequestException("Carrito vacío"));

        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Carrito vacío");
        }

        // Calcula subtotal
        BigDecimal subtotal = cart.getItems().stream()
                .map(ci -> ci.getProduct().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Simula tax y shipping (por ejemplo 10% tax y $5 envío fijo)
        BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(0.10));
        BigDecimal shipping = BigDecimal.valueOf(5.00);
        BigDecimal total = subtotal.add(tax).add(shipping);

        // Crea la sesión de checkout
        String sessionId = UUID.randomUUID().toString();
        CheckoutSession session = CheckoutSession.builder()
                .sessionId(sessionId)
                .user(user)
                .billingAddress(req.getBillingAddress())
                .paymentMethod(req.getPaymentMethod())
                .subtotal(subtotal)
                .tax(tax)
                .shipping(shipping)
                .total(total)
                .status(CheckoutSession.Status.pending)
                .build();
        sessionRepo.save(session);

        return CheckoutResponseDto.builder()
                .checkoutId(sessionId)
                .subtotal(subtotal)
                .tax(tax)
                .shipping(shipping)
                .total(total)
                .build();
    }

    @Override
    @Transactional
    public OrderResponseDto payAndCreateOrder(String userEmail, String sessionId, PaymentRequestDto req) {
        // 1) Recupera la sesión
        CheckoutSession session = sessionRepo.findById(sessionId)
                .orElseThrow(() -> new BadRequestException("Session inválida"));

        if (session.getStatus() != CheckoutSession.Status.pending) {
            throw new BadRequestException("Session ya procesada");
        }

        // 2) Simula la transacción de pago sin llamar a ningún proveedor
        PaymentTransaction tx = PaymentTransaction.builder()
                .session(session)
                .provider("mock")
                .providerRef("MOCK-" + UUID.randomUUID())
                .amount(session.getTotal())
                .currency("USD")
                .status(PaymentTransaction.Status.succeeded)
                .build();
        txRepo.save(tx);

        // 3) Marca la sesión como pagada
        session.setStatus(CheckoutSession.Status.paid);
        sessionRepo.save(session);

        // 4) Reusa tu OrderService para crear la orden real
        //    (tu createOrder usa el carrito y borra sus ítems)
        OrderResponseDto orderResp = orderService.createOrder(userEmail);

        return orderResp;
    }
}
