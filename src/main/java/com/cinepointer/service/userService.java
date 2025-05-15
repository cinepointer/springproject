package com.cinepointer.service;


import org.springframework.stereotype.Service;
import com.cinepointer.dto.usersDto;
import jakarta.servlet.http.HttpSession;

@Service
//1. 인터페이스 선언
public interface userService {
	 boolean registerUser(usersDto user);
	 usersDto login(String userId, String userPasswd, HttpSession session);
	 void logout(HttpSession session);
	 usersDto getUserInfo(int userNum);
	 void updateUser(usersDto user);
	 void deleteUser(int userNum);
	 usersDto findById(String userId);
	 
}

