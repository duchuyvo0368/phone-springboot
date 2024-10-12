package com.windshop.phone.repository;

import com.windshop.phone.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
	Page<User> findAllByRoleIsIn(List<String> role, Pageable pageable);
    Page<User> findByStatus(Integer status, Pageable pageable);
	User findByResetPasswordToken(String token);
}
