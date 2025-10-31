package com.ecommerce.order_service.service;

import com.ecommerce.order_service.DTO.CartRequest;
import com.ecommerce.order_service.DTO.CartResponse;
import com.ecommerce.order_service.model.Cart;
import com.ecommerce.order_service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public boolean addToCart(Long userId, CartRequest cartRequest) {
        CartResponse cartResponse = new CartResponse();
//        Optional<Product> productOpt = productRepository.findById(Long.valueOf(cartRequest.getProductId()));
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if (productOpt.isEmpty()) {
//            cartResponse.setMessage("Product Not found");
//            cartResponse.setStatus(false);
//            return cartResponse;
//        }
//
//        Product product = productOpt.get();
//        User user = userOpt.get();

//        if (product.getStockQuantity() < cartRequest.getQuantity()) {
//            cartResponse.setMessage("Requested quantity is not available");
//            cartResponse.setStatus(false);
//            return cartResponse;
//        }
//
//        if (userOpt.isEmpty()) {
//            cartResponse.setMessage("User Not found");
//            cartResponse.setStatus(false);
//            return cartResponse;
//        }

        Cart existingProduct = cartRepository.findByUserIdAndProductId(userId, Long.valueOf(cartRequest.getProductId()));
        if (existingProduct != null) {
            existingProduct.setQuantity(existingProduct.getQuantity() + cartRequest.getQuantity());
//            existingProduct.setPrice(existingProduct.getPrice().multiply(BigDecimal.valueOf(cartRequest.getQuantity())));
            existingProduct.setPrice(BigDecimal.valueOf(1000));
            cartRepository.save(existingProduct);
            cartResponse.setMessage("Item added successfully");
            cartResponse.setStatus(true);
        } else {
            Cart cart = new Cart();
            cart.setQuantity(cartRequest.getQuantity());
            cart.setUserId(userId);
            cart.setProductId(Long.valueOf(cartRequest.getProductId()));
//            cart.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartRequest.getQuantity())));
            cart.setPrice(BigDecimal.valueOf(1000));
            cartRepository.save(cart);
            cartResponse.setMessage("Item added successfully");
            cartResponse.setStatus(true);
        }
return true;
    }


    public boolean deleteFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId);

        if (cart != null) {
            cartRepository.delete(cart);
            return true;
        }
        return  false;

    }

    public List<Cart> fetchAllItems(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }
}
