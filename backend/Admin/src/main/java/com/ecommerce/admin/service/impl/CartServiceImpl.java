package com.windshop.phone.service.impl;

import com.windshop.phone.entity.Cart;
import com.windshop.phone.repository.CartRepository;
import com.windshop.phone.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Cart findByUserEmail(String email, Integer status) {
        List<Cart> cartList = cartRepository.findAllByEmailUserAndStatusOrderByCreatedDateDesc(email, status);
        if (cartList.isEmpty()){
            return null;
        }
        return cartList.get(0);
    }

    public void delete(Cart cart){
        cart.setStatus(0);
        cartRepository.saveAndFlush(cart);
    }

}
