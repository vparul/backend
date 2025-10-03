package com.app.ecom.controller;

import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.dto.CartRequest;
import com.app.ecom.dto.CartResponse;
import com.app.ecom.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-USER-ID") String userId, @RequestBody CartRequest cartRequest) {
        CartResponse response = cartService.addToCart(userId, cartRequest);
        return response.getStatus() ? ResponseEntity.ok(response.getMessage()) : ResponseEntity.badRequest().body(response.getMessage());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteFromCart(@RequestHeader("X-USER-ID") String userId, @PathVariable String productId) {
        CartResponse response = cartService.deleteFromCart(userId, productId);
        return response.getStatus() ? ResponseEntity.ok(response.getMessage()) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getAllCartItems(@RequestHeader("X-USER-ID") String userId) {
        List<CartItemResponse> response = cartService.fetchAllItems(userId);
        return ResponseEntity.ok().body(response);
    }

}
