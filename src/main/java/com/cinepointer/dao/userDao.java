package com.cinepointer.dao;

import com.cinepointer.dto.usersDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface userDao {
	
	usersDto selectUserById(String userId);
    void insertUser(usersDto user);
    
    // 회원정보 수정
    void updateUser(usersDto user);

    // 회원탈퇴(삭제)
    void deleteUser(@Param("userId") String userId);

    // 전체 회원 리스트 조회 (권한 관리용)
    List<usersDto> selectAllUsers();
	usersDto findByUserId(String userId);
	usersDto findByUserNum(int userNum);
	void deleteUser(int userNum);
}
