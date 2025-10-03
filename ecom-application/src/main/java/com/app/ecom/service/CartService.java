package com.app.ecom.service;

import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.dto.CartRequest;
import com.app.ecom.dto.CartResponse;
import com.app.ecom.model.Cart;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartResponse addToCart(String userId, CartRequest cartRequest) {
        CartResponse cartResponse = new CartResponse();
        Optional<Product> productOpt = productRepository.findById(Long.valueOf(cartRequest.getProductId()));
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (productOpt.isEmpty()) {
            cartResponse.setMessage("Product Not found");
            cartResponse.setStatus(false);
            return cartResponse;
        }

        Product product = productOpt.get();
        User user = userOpt.get();

        if (product.getStockQuantity() < cartRequest.getQuantity()) {
            cartResponse.setMessage("Requested quantity is not available");
            cartResponse.setStatus(false);
            return cartResponse;
        }

        if (userOpt.isEmpty()) {
            cartResponse.setMessage("User Not found");
            cartResponse.setStatus(false);
            return cartResponse;
        }

        Cart existingProduct = cartRepository.findByUserAndProduct(user, product);
        if (existingProduct != null) {
            existingProduct.setQuantity(existingProduct.getQuantity() + cartRequest.getQuantity());
            existingProduct.setPrice(existingProduct.getPrice().multiply(BigDecimal.valueOf(cartRequest.getQuantity())));
            cartRepository.save(existingProduct);
            cartResponse.setMessage("Item added successfully");
            cartResponse.setStatus(true);
        } else {
            Cart cart = new Cart();
            cart.setQuantity(cartRequest.getQuantity());
            cart.setUser(user);
            cart.setProduct(product);
            cart.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartRequest.getQuantity())));
            cartRepository.save(cart);
            cartResponse.setMessage("Item added successfully");
            cartResponse.setStatus(true);
        }

        return cartResponse;
    }


    public CartResponse deleteFromCart(String userId, String productId) {
        CartResponse cartResponse = new CartResponse();
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        Optional<Product> productOpt = productRepository.findById(Long.valueOf(productId));

        if (userOpt.isEmpty()) {
            cartResponse.setMessage("User not found");
            cartResponse.setStatus(false);
            return cartResponse;
        }

        User user = userOpt.get();
        Product product = productOpt.get();

        if (productOpt.isPresent()) {
            cartRepository.deleteByUserAndProduct(user, product);
            cartResponse.setMessage("Item deleted successfully");
            cartResponse.setStatus(true);

        }
        return cartResponse;
    }

    public List<CartItemResponse> fetchAllItems(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(user -> cartRepository.findByUser(user).stream()
                        .map(cart -> {
                            CartItemResponse response = new CartItemResponse();
                            response.setPrice(cart.getPrice());
                            response.setQuantity(cart.getQuantity());
                            response.setUser(cart.getUser());
                            response.setProduct(cart.getProduct());
                            return response;
                        })
                        .toList())
                .orElseGet(List::of);
    }

}
