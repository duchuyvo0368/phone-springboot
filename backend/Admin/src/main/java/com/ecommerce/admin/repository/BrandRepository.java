package com.windshop.phone.repository;

import com.windshop.phone.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Page<Brand> findAllByStatus(Pageable page, Integer status);
}
