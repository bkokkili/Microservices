package com.seafoodveggies.order_service.dto;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long userId;
    private Long addressId;
    private List<OrderItemDTO> items;
}
