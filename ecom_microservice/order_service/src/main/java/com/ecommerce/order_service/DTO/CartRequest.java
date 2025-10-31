package com.ecommerce.order_service.DTO;

import lombok.Data;

@Data
public class CartRequest {
    private String productId;
    private Integer quantity;
}
