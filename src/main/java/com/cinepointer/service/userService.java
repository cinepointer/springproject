package com.cinepointer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cinepointer.dao.userDao;
import com.cinepointer.dto.usersDto;

import jakarta.servlet.http.HttpSession;

@Service
public class userService {

    private final userDao userDao;
    private final PasswordEncoder passwordEncoder;

    // 세션 관리를 위한 상수
    private static final String USER_SESSION_KEY = "loggedInUser";

    // 생성자 주입 방식 사용 (권장 방식)
    @Autowired
    public userService(userDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(usersDto user) {

        // 비밀번호 암호화
        user.setUserPasswd(passwordEncoder.encode(user.getUserPasswd()));
        // 아이디 중복 체크
        usersDto existing = userDao.selectUserById(user.getUserId());
        if (existing != null) {
            return false; // 중복

        // 1. 아이디 중복 검사
        usersDto existingUser = userDao.selectUserById(user.getUserId());
        if (existingUser != null) {
            return false; // 아이디 이미 존재

        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(user.getUserPasswd());
        user.setUserPasswd(encodedPassword);
        System.out.println("비밀번호 암호화 및 db저장");
        // 3. 회원 정보 DB에 저장
        userDao.insertUser(user);

        return true;
    }
    }

    // 로그인
    public usersDto login(String userId, String userPasswd, HttpSession session) {
        usersDto user = userDao.selectUserById(userId);
        if (user != null && passwordEncoder.matches(userPasswd, user.getUserPasswd())) {
            session.setAttribute(USER_SESSION_KEY, user);
            return user;
        }
        return null;
    }

    // 로그아웃
    public void logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }

    // 회원정보 조회 (userId로 조회)
    public usersDto getUserInfo(String userId) {
        return userDao.selectUserById(userId);
    }

    // 회원정보 수정
    public void updateUser(usersDto user) {
        // 비밀번호 변경시 암호화
        if (user.getUserPasswd() != null && !user.getUserPasswd().isEmpty()) {
            user.setUserPasswd(passwordEncoder.encode(user.getUserPasswd()));
        }
        userDao.updateUser(user);
    }

    // 회원탈퇴
    public void deleteUser(String userId) {
        userDao.deleteUser(userId);
    }
}
