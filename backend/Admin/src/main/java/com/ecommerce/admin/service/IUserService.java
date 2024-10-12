package com.windshop.phone.service;

import com.windshop.phone.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    void save(User user);

    User findByEmail(String email);

    Page<User> findAllByStatus(Pageable pageable, Integer status);
}
