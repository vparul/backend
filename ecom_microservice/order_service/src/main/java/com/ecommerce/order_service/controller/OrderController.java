package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.DTO.OrderResponse;
import com.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    private ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-USER-ID") String userId) {
        orderService.createOrder(Long.valueOf(userId));
        return ResponseEntity.ok().build();
    }

}
