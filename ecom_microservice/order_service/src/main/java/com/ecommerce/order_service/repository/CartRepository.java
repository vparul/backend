package com.ecommerce.order_service.repository;

import com.ecommerce.order_service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserIdAndProductId(Long userId, Long productId);

    List<Cart> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
