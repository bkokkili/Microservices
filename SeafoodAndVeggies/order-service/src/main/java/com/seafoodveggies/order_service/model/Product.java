package com.seafoodveggies.order_service.model;
import java.time.LocalDateTime;

public record Product(
        Long id,
        String name,
        String description,
        double price,
        int stockQuantity,
        String category,
        String imageUrl,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
