package com.windshop.phone.service;

import com.windshop.phone.entity.Cart;


public interface ICartService {
    void save(Cart cart);
    Cart findByUserEmail(String email, Integer status);
}
