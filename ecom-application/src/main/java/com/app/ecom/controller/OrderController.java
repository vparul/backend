package com.app.ecom.controller;

import com.app.ecom.dto.OrderResponse;
import com.app.ecom.model.Order;
import com.app.ecom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    private ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-USER-ID") String userId) {
        orderService.createOrder(userId);
        return ResponseEntity.ok().build();
    }

}
