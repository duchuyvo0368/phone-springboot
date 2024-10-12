package com.windshop.phone.service.impl;

import com.windshop.phone.entity.User;
import com.windshop.phone.model.UserDto;
import com.windshop.phone.repository.UserRepository;
import com.windshop.phone.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Service
public class UserServiceImpl implements IUserService {

    private static final String ROOT_PATH = "E:\\wind-shop_Doan\\upload\\";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void update(UserDto user) throws IOException {
        User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userDb = findByEmail(userContext.getEmail());

        if (StringUtils.isNotEmpty(user.getFirstName())) {
            userDb.setFirstName(user.getFirstName());
        }

        if (StringUtils.isNotEmpty(user.getLastName())) {
            userDb.setLastName(user.getLastName());
        }

        if (StringUtils.isNotEmpty(user.getEmail())) {
            userDb.setEmail(user.getEmail());
        }

        if (StringUtils.isNotEmpty(user.getAddress())) {
            userDb.setAddress(user.getAddress());
        }

        if (StringUtils.isNotEmpty(user.getPhoneNumber())) {
            userDb.setPhoneNumber(user.getPhoneNumber());
        }

        if (user.getImage() != null) {
            new File(ROOT_PATH + userDb.getAvatar()).delete();
            user.getImage().transferTo(new File(
                    ROOT_PATH + user.getImage().getOriginalFilename()));
            userDb.setAvatar(user.getImage().getOriginalFilename());
        }
        userRepository.save(userDb);
    }

    @Override
    public Page<User> findAllByStatus(Pageable pageable, Integer status) {
        return userRepository.findByStatus(status, pageable);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}