package com.app.ecom.repository;

import com.app.ecom.model.Cart;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<Cart> findByUser(User user);
}
