package com.cinepointer.dao;

import com.cinepointer.dto.usersDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface userDao {
	// 사용자 아이디로 사용자 정보 조회 (로그인, 중복체크 등에서 사용)
	usersDto selectUserById(String userId);
	
	// 사용자 번호로 사용자 정보 조회 (마이페이지 등에서 사용)
	usersDto findByUserNum(int userNum);
	
	// 사용자 정보 저장 (회원가입)
	void insertUser(usersDto user);
	
	// 사용자 정보 수정 (비밀번호 포함)
	void updateUser(usersDto user);
	
	// 사용자 삭제 (회원탈퇴)
	void deleteUser(int userNum);


}
