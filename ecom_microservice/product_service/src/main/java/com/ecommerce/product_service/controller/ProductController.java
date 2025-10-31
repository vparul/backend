package com.ecommerce.product_service.controller;


import com.ecommerce.product_service.DTO.ProductRequest;
import com.ecommerce.product_service.DTO.ProductResponse;
import com.ecommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    private ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.fetchAllProducts());
    }

    @PostMapping
    private ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProduct(productRequest);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/{id}")
    private ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Boolean isUpdated = productService.updateProduct(id, productRequest);
        if (isUpdated) return ResponseEntity.ok("User updated successfully");
        else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProduct(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    private ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }


}
