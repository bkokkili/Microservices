package com.seafoodveggies.order_service.util;

import com.seafoodveggies.order_service.client.ProductFeignClient;
import com.seafoodveggies.order_service.dto.*;
import com.seafoodveggies.order_service.entity.Order;
import com.seafoodveggies.order_service.entity.OrderItem;
import com.seafoodveggies.order_service.model.Address;
import com.seafoodveggies.order_service.model.Product;
import com.seafoodveggies.order_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    @Autowired
    private ProductFeignClient productClient;

    public OrderResponseDTO toOrderResponseDTO(Order order, User user, Address address) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setShippingAddress(address);
        dto.setItems(order.getItems().stream()
                .map(this::mapOrderItemToDTO)
                .collect(Collectors.toList()));
        return dto;
    }


    private  OrderItemResponseDTO mapOrderItemToDTO(OrderItem item) {
        Product product = productClient.getProductById(item.getProductId());
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setProductId(product.id());
        dto.setProductName(product.name());
        dto.setQuantity(item.getQuantity());
        dto.setPriceAtPurchase(item.getPriceAtPurchase());
        return dto;
    }

    public Address getAddressById(User user, long addressId) {
        return user.getAddresses()
                .stream()
                .filter(address -> address.id() == addressId)
                .findFirst()
                .orElse(null);
    }



}

