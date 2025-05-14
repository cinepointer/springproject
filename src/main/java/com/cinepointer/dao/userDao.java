package com.cinepointer.dao;

import com.cinepointer.dto.usersDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface userDao {
    usersDto selectUserById(@Param("user_id") String user_id);
    void insertUser(usersDto user);

    // 회원정보 수정
    void updateUser(usersDto user);

    // 회원탈퇴(삭제)
    void deleteUser(@Param("user_id") String user_id);

    // 전체 회원 리스트 조회 (권한 관리용)
    List<usersDto> selectAllUsers();
}
