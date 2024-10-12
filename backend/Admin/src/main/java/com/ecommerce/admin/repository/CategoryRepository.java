package com.windshop.phone.repository;

import com.windshop.phone.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findAllByStatus(Integer status, Pageable pageable);
}
