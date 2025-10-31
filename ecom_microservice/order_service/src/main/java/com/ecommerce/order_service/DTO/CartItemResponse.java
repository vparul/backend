package com.ecommerce.order_service.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {
    private Long userId;
    private Long productId;
    private BigDecimal price;
    private Integer quantity;
}
