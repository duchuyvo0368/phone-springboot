package com.windshop.phone.service;

import com.windshop.phone.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface IProductService {
    void save(MultipartFile[] productImages, Product product) throws IOException;

    Page<Product> pageProduct(Pageable pageable);

    Optional<Product> findById(Integer id);
}
