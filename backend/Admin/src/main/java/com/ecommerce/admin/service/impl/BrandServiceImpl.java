package com.windshop.phone.service.impl;

import com.windshop.phone.entity.Brand;
import com.windshop.phone.repository.BrandRepository;
import com.windshop.phone.service.IBrandSerVice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements IBrandSerVice {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public Page<Brand> getBrands(Pageable pageable) {
        return brandRepository.findAllByStatus(pageable, 1);
    }
}
