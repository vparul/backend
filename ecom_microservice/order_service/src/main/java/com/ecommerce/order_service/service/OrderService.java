package com.ecommerce.order_service.service;

import com.ecommerce.order_service.DTO.CartItemResponse;
import com.ecommerce.order_service.DTO.OrderItemDTO;
import com.ecommerce.order_service.DTO.OrderResponse;
import com.ecommerce.order_service.model.Cart;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderItem;
import com.ecommerce.order_service.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;

    public Optional<OrderResponse> createOrder(Long userId) {
        List<Cart> cartItems = cartService.fetchAllItems(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal totalPrice = cartItems.stream().map(Cart::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setProductId(item.getProductId());
            orderItem.setOrder(order);
            orderItem.setQuantity(item.getQuantity());
            return orderItem;
        }).toList();
        order.setItems(orderItems);

        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(order));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setItems(order.getItems().stream().map(item -> {
            OrderItemDTO orderItem = new OrderItemDTO();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            return orderItem;
        }).toList());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setCreatedAt(order.getCreatedAt());
        return orderResponse;
    }
}
