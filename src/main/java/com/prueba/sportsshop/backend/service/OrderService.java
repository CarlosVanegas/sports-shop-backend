package com.prueba.sportsshop.backend.service;

import com.prueba.sportsshop.backend.dto.OrderDetailResponseDto;
import com.prueba.sportsshop.backend.dto.OrderResponseDto;
import com.prueba.sportsshop.backend.dto.OrderSummaryDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(String userEmail);
    List<OrderSummaryDto> listOrders(String userEmail);
    OrderDetailResponseDto getOrderDetails(String userEmail, Integer orderId);

}
