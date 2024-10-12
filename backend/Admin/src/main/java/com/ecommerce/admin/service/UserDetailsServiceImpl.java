package com.windshop.phone.service;

import com.windshop.phone.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            if(ObjectUtils.isEmpty(email)){
                throw new Exception();
            }
            String sql = "SELECT * FROM tbl_users where email ='" + email +"'";
            Query query = entityManager.createNativeQuery(sql, User.class);
            return (User) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
