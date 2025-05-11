package com.prueba.sportsshop.backend.service;

import com.prueba.sportsshop.backend.dto.OrderDetailResponseDto;
import com.prueba.sportsshop.backend.dto.OrderResponseDto;
import com.prueba.sportsshop.backend.dto.OrderSummaryDto;
import com.prueba.sportsshop.backend.entity.CartItem;
import com.prueba.sportsshop.backend.entity.Cart;
import com.prueba.sportsshop.backend.entity.Order;
import com.prueba.sportsshop.backend.entity.OrderDetail;
import com.prueba.sportsshop.backend.entity.Order.Status;
import com.prueba.sportsshop.backend.entity.User;
import com.prueba.sportsshop.backend.exception.BadRequestException;
import com.prueba.sportsshop.backend.exception.ResourceNotFoundException;
import com.prueba.sportsshop.backend.repository.CartItemRepository;
import com.prueba.sportsshop.backend.repository.CartRepository;
import com.prueba.sportsshop.backend.repository.OrderDetailRepository;
import com.prueba.sportsshop.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final OrderRepository orderRepo;
    private final OrderDetailRepository detailRepo;

    @Override
    @Transactional
    public OrderResponseDto createOrder(String userEmail) {
        // 1. Recuperar usuario y carrito
        User user = userService.findByEmail(userEmail);
        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new BadRequestException("No tienes items en el carrito"));

        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Carrito vacío");
        }

        // 2. Crear la orden
        Order order = Order.builder()
                .user(user)
                .shippingAddress(user.getShippingAddress())
                .status(Status.valueOf("pending"))
                .build();
        order = orderRepo.save(order);

        // 3. Para cada ítem del carrito, crear detalle de orden
        for (CartItem ci : cart.getItems()) {
            OrderDetail od = OrderDetail.builder()
                    .order(order)
                    .product(ci.getProduct())
                    .quantity(ci.getQuantity())
                    .priceAtTime(ci.getProduct().getPrice())
                    .build();
            detailRepo.save(od);
        }

        // 4. Borrar carrito e ítems
        cartItemRepo.deleteAll(cart.getItems());
        cartRepo.delete(cart);

        // 5. Devolver respuesta
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .message("Pedido confirmado con éxito")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderSummaryDto> listOrders(String userEmail) {
        User user = userService.findByEmail(userEmail);
        return orderRepo.findByUser(user).stream().map(order -> {
            BigDecimal total = order.getDetails().stream()
                    .map(d -> d.getPriceAtTime().multiply(BigDecimal.valueOf(d.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return OrderSummaryDto.builder()
                    .orderId(order.getId())
                    .orderDate(order.getOrderDate())
                    .status(order.getStatus().name())
                    .totalAmount(total)
                    .build();
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailResponseDto getOrderDetails(String userEmail, Integer orderId) {
        User user = userService.findByEmail(userEmail);
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("No tienes acceso a esa orden");
        }

        List<OrderDetailResponseDto.Item> items = order.getDetails().stream()
                .map(d -> OrderDetailResponseDto.Item.builder()
                        .productId(d.getProduct().getId())
                        .description(d.getProduct().getDescription())
                        .imageUrl(d.getProduct().getImageUrl())
                        .quantity(d.getQuantity())
                        .priceAtTime(d.getPriceAtTime())
                        .subtotal(d.getPriceAtTime().multiply(
                                BigDecimal.valueOf(d.getQuantity())))
                        .build())
                .toList();

        return OrderDetailResponseDto.builder()
                .orderId(order.getId())
                .status(order.getStatus().name())
                .items(items)
                .build();
    }


}
