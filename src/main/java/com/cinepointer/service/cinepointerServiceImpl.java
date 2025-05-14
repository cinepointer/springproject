package com.cinepointer.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cinepointer.dao.userDao;
import com.cinepointer.dto.usersDto;
import java.util.List;

@Service
public class cinepointerServiceImpl implements cinepointerService {

    private final userDao userDao;
    private final PasswordEncoder passwordEncoder;

    public cinepointerServiceImpl(userDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public usersDto register(usersDto user) {
        user.setUser_passwd(passwordEncoder.encode(user.getUser_passwd()));
        user.setUser_status("A"); // 활성화 상태
        userDao.insertUser(user);
        return user;
    }

    @Override
    public usersDto findById(String user_id) {
        return userDao.selectUserById(user_id);
    }

    @Override
    public void updateUser(usersDto user) {
        // 비밀번호가 변경되었을 때만 암호화 수행
        if (user.getUser_passwd() != null && !user.getUser_passwd().isEmpty()) {
            user.setUser_passwd(passwordEncoder.encode(user.getUser_passwd()));
        }
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(String user_id) {
        userDao.deleteUser(user_id);
    }

    @Override
    public List<usersDto> findAllUsers() {
        return userDao.selectAllUsers();
    }
}
