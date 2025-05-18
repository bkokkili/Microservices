package com.seafoodveggies.order_service.service;

import com.seafoodveggies.order_service.dto.OrderItemDTO;
import com.seafoodveggies.order_service.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    OrderResponseDTO placeOrder(Long userId, Long addressId, List<OrderItemDTO> itemsRequest);

    OrderResponseDTO updateOrderStatus(Long orderId, String status);

    List<OrderResponseDTO> getOrdersByUserId(Long userId);

    OrderResponseDTO getOrderDetails(Long userId, Long orderId);
}
