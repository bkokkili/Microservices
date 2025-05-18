package com.seafoodveggies.order_service.controller;

import com.seafoodveggies.order_service.dto.*;
import com.seafoodveggies.order_service.entity.Order;
import com.seafoodveggies.order_service.exception.ResourceNotFoundException;
import com.seafoodveggies.order_service.model.Address;
import com.seafoodveggies.order_service.model.User;
import com.seafoodveggies.order_service.repo.OrderRepository;
import com.seafoodveggies.order_service.service.OrderService;
import com.seafoodveggies.order_service.util.OrderMapper;
import com.seafoodveggies.order_service.util.PdfGeneratorUtil;
import com.seafoodveggies.order_service.client.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserFeignClient userClient;

    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponseDTO order = orderService.placeOrder(
                orderRequest.getUserId(),
                orderRequest.getAddressId(),
                orderRequest.getItems()
        );
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderResponseDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}/order/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderDetails(
            @PathVariable Long userId,
            @PathVariable Long orderId) {

        OrderResponseDTO response = orderService.getOrderDetails(userId, orderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequest request) {
        OrderResponseDTO updatedOrder = orderService.updateOrderStatus(orderId, request.getStatus());
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/invoice/{orderId}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        User user = userClient.getUserById(order.getUserId());
        Address address = orderMapper.getAddressById(user, order.getAddressId());

        OrderResponseDTO orderDTO = orderMapper.toOrderResponseDTO(order, user, address);

        try {
            byte[] pdf = PdfGeneratorUtil.generateOrderInvoice(orderDTO);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order-" + orderId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF invoice", e);
        }
    }


}
