package com.windshop.phone.service.impl;

import com.windshop.phone.entity.SaleOrder;
import com.windshop.phone.entity.User;
import com.windshop.phone.repository.SaleOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SaleOrderService {

    @Autowired
    private SaleOrderRepository saleOrderRepository;

    @Autowired
    private UserServiceImpl userService;

    public Page<SaleOrder> findAllSaleOrder(Pageable pageable) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userDb = userService.findByEmail(user.getEmail());
        return saleOrderRepository.findAllByUserIdAndStatusOrderByCreatedDateDesc(userDb.getId(), 1, pageable);
    }

    public SaleOrder findById(Integer id) {
        return saleOrderRepository.findById(id).orElse(null);
    }

    public void updateSaleOrder(SaleOrder saleOrder) {
        saleOrderRepository.save(saleOrder);
    }
}
