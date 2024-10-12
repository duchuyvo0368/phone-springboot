package com.windshop.phone.repository;

import com.windshop.phone.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByEmailUserAndStatusOrderByCreatedDateDesc(String emailUser, Integer status);
}
