package com.windshop.phone.repository;

import com.windshop.phone.entity.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCartRepository extends JpaRepository<ProductCart, Integer> {
}
