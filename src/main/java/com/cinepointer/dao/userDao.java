package com.cinepointer.dao;

import com.cinepointer.dto.UsersDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    UsersDto selectUserById(@Param("userId") String userId);
    void insertUser(UsersDto user);

    // 회원정보 수정
    void updateUser(UsersDto user);

    // 회원탈퇴(삭제)
    void deleteUser(@Param("userId") String userId);

    // 전체 회원 리스트 조회 (권한 관리용)
    List<UsersDto> selectAllUsers();
}
