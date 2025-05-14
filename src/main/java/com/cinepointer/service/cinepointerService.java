package com.cinepointer.service;

import com.cinepointer.dto.usersDto;
import java.util.List;

public interface cinepointerService {
    usersDto register(usersDto user);
    usersDto findById(String user_id);

    // 회원정보 수정
    void updateUser(usersDto user);

    // 회원탈퇴
    void deleteUser(String user_id);

    // 전체 회원 리스트 조회
    List<usersDto> findAllUsers();
}
