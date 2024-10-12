package com.windshop.phone.service;

import com.windshop.phone.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBrandSerVice {

    void save(Brand brand);

    Page<Brand> getBrands(Pageable pageable);
}
