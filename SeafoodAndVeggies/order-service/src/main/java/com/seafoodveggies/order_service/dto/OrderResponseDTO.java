package com.seafoodveggies.order_service.dto;
import com.seafoodveggies.order_service.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String orderStatus;
    private String userName;
    private String email;
    private Address shippingAddress;
    private List<OrderItemResponseDTO> items;

}