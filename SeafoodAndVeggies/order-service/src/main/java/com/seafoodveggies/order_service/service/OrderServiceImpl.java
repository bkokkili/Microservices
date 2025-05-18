package com.seafoodveggies.order_service.service;

import com.seafoodveggies.order_service.dto.*;
import com.seafoodveggies.order_service.entity.Order;
import com.seafoodveggies.order_service.entity.OrderItem;
import com.seafoodveggies.order_service.exception.ResourceNotFoundException;
import com.seafoodveggies.order_service.model.Address;
import com.seafoodveggies.order_service.model.Product;
import com.seafoodveggies.order_service.model.User;
import com.seafoodveggies.order_service.repo.OrderRepository;
import com.seafoodveggies.order_service.util.OrderMapper;
import com.seafoodveggies.order_service.client.ProductFeignClient;
import com.seafoodveggies.order_service.client.UserFeignClient;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static  final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public OrderResponseDTO placeOrder(Long userId, Long addressId, List<OrderItemDTO> itemsRequest) {
        User user = userFeignClient.getUserById(userId);

        Address shippingAddress = orderMapper.getAddressById(user,addressId);

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("PLACED");
        order.setAddressId(addressId);
        order.setUserId(userId);

        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemResponseDTO> orderItemResponseDTOS = new ArrayList<>();
        double totalAmount = 0;

        for (OrderItemDTO req : itemsRequest) {
            Product product = productFeignClient.getProductById(req.getProductId());

            if (product.stockQuantity() < req.getQuantity()) {
                throw new ResourceNotFoundException("Insufficient stock for product: " + product.name());
            }

            OrderItem item = new OrderItem();
            item.setProductId(req.getProductId());
            item.setQuantity(req.getQuantity());
            item.setPriceAtPurchase(product.price() * req.getQuantity());
            item.setOrder(order);

            orderItems.add(item);
            totalAmount += item.getPriceAtPurchase();

            OrderItemResponseDTO orderItemResponse = new OrderItemResponseDTO();
            orderItemResponse.setProductName(product.name());
            orderItemResponse.setQuantity(req.getQuantity());
            orderItemResponse.setProductId(req.getProductId());
            orderItemResponse.setPriceAtPurchase(product.price());

            orderItemResponseDTOS.add(orderItemResponse);

           // product.setStockQuantity(product.getStockQuantity() - req.getQuantity());
        }

        order.setTotalAmount(totalAmount);
        order.setItems(orderItems);
        orderRepository.save(order);  // Save order and cascade items if set

        return new OrderResponseDTO(
                order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                user.getUserName(),
                user.getEmail(),
                shippingAddress,
                orderItemResponseDTOS
        );
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        User user = userFeignClient.getUserById(userId);
        return orders.stream()
                .map(order -> {
                    Address address = orderMapper.getAddressById(user, order.getAddressId());
                    return orderMapper.toOrderResponseDTO(order, user, address);
                })
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO getOrderDetails(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
        User user = userFeignClient.getUserById(userId);
        if (!user.getId().equals(userId)) {
            throw new RuntimeException("Access denied: Order does not belong to the user.");
        }

        Address address = orderMapper.getAddressById(user, order.getAddressId());

        return orderMapper.toOrderResponseDTO(order, user, address);
    }

    @Override
    public OrderResponseDTO updateOrderStatus(Long orderId, String status) {
        LOG.info("Updating status for order ID: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        String normalizedStatus = status.toUpperCase();
        if (!List.of("PENDING", "ACCEPTED", "CANCELLED", "DELIVERED", "SHIPPED").contains(normalizedStatus)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        order.setOrderStatus(normalizedStatus);
        try {
            orderRepository.save(order);
            LOG.info("Order saved successfully");
        } catch (Exception e) {
            LOG.error("Exception while saving order", e);
        }
        User user = userFeignClient.getUserById(order.getUserId());

        Address address = orderMapper.getAddressById(user, order.getAddressId());

        LOG.info("Order status updated to {}", normalizedStatus);
        return orderMapper.toOrderResponseDTO(order, user, address);
    }
}

