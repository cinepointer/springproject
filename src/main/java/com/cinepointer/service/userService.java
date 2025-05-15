package com.cinepointer.service;


import java.util.List;
import org.springframework.stereotype.Service;
import com.cinepointer.dto.usersDto;
import jakarta.servlet.http.HttpSession;

@Service
//1. 인터페이스 선언
public interface userService {

	//회원가입, 로그인, 로그아웃
	boolean registerUser(usersDto user);
	usersDto login(String userId, String userPasswd, HttpSession session);
	void logout(HttpSession session);
	 
	//관리자용기능
	usersDto getUserInfo(int userNum);
	void updateUser(usersDto user);
	void deleteUser(int userNum);
	usersDto findById(String userId);
	List<usersDto> findAll();

}

