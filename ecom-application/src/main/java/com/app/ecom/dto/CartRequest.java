package com.app.ecom.dto;

import lombok.Data;

@Data
public class CartRequest {
    private String productId;
    private Integer quantity;
}
