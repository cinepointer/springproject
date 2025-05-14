package com.cinepointer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cinepointer.dao.userDao;
import com.cinepointer.dto.usersDto;

import jakarta.servlet.http.HttpSession;

@Service
public class userServiceImpl implements userService, UserDetailsService {

    private final userDao userDao;
    private final PasswordEncoder passwordEncoder;

    private static final String USER_SESSION_KEY = "loggedInUser";

    @Autowired
    public userServiceImpl(userDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    // Spring Security에서 로그인 시 호출되는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        usersDto user = userDao.selectUserById(username);
        if (user == null) {
            throw new UsernameNotFoundException("해당 아이디의 사용자를 찾을 수 없습니다: " + username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserId())
                .password(user.getUserPasswd()) // 암호화된 비밀번호
                .roles("USER") // 필요 시 USER, ADMIN 등
                .build();
    }

    //회원가입
    @Override
    public boolean registerUser(usersDto user) {
        usersDto existingUser = userDao.selectUserById(user.getUserId());
        if (existingUser != null) {
            return false; // 아이디 중복
        }

        user.setUserPasswd(passwordEncoder.encode(user.getUserPasswd()));
        userDao.insertUser(user);
        return true;
    }

    @Override
    public usersDto login(String userId, String userPasswd, HttpSession session) {
        usersDto user = userDao.selectUserById(userId);
        if (user != null && passwordEncoder.matches(userPasswd, user.getUserPasswd())) {
            session.setAttribute(USER_SESSION_KEY, user);
            return user;
        }
        return null;
    }

    @Override
    public void logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }

    @Override
    public usersDto getUserInfo(int userNum) {
        return userDao.findByUserNum(userNum);
    }

    @Override
    public void updateUser(usersDto user) {
        if (user.getUserPasswd() != null && !user.getUserPasswd().isEmpty()) {
            user.setUserPasswd(passwordEncoder.encode(user.getUserPasswd()));
        }
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(int userNum) {
        userDao.deleteUser(userNum);
    }
    
    @Override
    public usersDto findById(String userId) {
        return userDao.selectUserById(userId);
    }
}

