package com.cinepointer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cinepointer.dao.userDao;
import com.cinepointer.dto.usersDto;

@Service
public class customUserDetailsService implements UserDetailsService {

    @Autowired
    private userDao userDao;

    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
        usersDto user = userDao.selectUserById(user_id);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with id: " + user_id);
        }
        boolean enabled = "A".equals(user.getUser_status());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUser_id())
                .password(user.getUser_passwd())
                .roles(enabled ? "USER" : "INACTIVE")
                .disabled(!enabled)
                .build();
    }
}
