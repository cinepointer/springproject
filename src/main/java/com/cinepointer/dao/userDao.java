package com.cinepointer.dao;

import com.cinepointer.dto.usersDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface userDao {
    void insertUser(usersDto user);
    usersDto findByUserId(@Param("userId") String userId);
    usersDto findByUserNum(@Param("userNum") int userNum);
    void updateUser(usersDto user);
    void deleteUser(@Param("userNum") int userNum);

}
