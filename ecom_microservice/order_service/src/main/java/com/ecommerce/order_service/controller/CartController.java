package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.DTO.CartRequest;
import com.ecommerce.order_service.DTO.CartResponse;
import com.ecommerce.order_service.model.Cart;
import com.ecommerce.order_service.service.CartService;
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
        Boolean response = cartService.addToCart(Long.valueOf(userId), cartRequest);
        return response ? ResponseEntity.ok("Item added successfully") : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteFromCart(@RequestHeader("X-USER-ID") Long userId, @PathVariable Long productId) {
        Boolean response = cartService.deleteFromCart(userId, productId);
        return response ? ResponseEntity.ok("Item deleted successfully") : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCartItems(@RequestHeader("X-USER-ID") String userId) {
        List<Cart> response = cartService.fetchAllItems(Long.valueOf(userId));
        return ResponseEntity.ok().body(response);
    }

}
