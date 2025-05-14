package com.cinepointer.dao;

import com.cinepointer.dto.usersDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface userDao {
<<<<<<< HEAD

    // 아이디로 회원 조회
    usersDto selectUserById(@Param("userId") String userId);

    // 회원 등록
=======
	
	usersDto selectUserById(String userId);
>>>>>>> branch 'master' of https://github.com/cinepointer/springproject.git
    void insertUser(usersDto user);
    
    // 회원정보 수정
    void updateUser(usersDto user);

    // 회원탈퇴(삭제) - userId로 삭제
    void deleteUser(@Param("userId") String userId);

    // 회원탈퇴(삭제) - userNum으로 삭제 (오버로딩)
    void deleteUser(int userNum);

    // 전체 회원 리스트 조회 (권한 관리용)
    List<usersDto> selectAllUsers();

    // 아이디로 회원 조회 (중복이지만, 둘 중 하나만 사용해도 무방)
    usersDto findByUserId(String userId);

    // 회원번호로 회원 조회
    usersDto findByUserNum(int userNum);
}
