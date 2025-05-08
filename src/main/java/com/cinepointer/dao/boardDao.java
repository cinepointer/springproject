package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cinepointer.dto.boardDto;

@Mapper
public interface boardDao {
	public List<boardDto> getList();

	public int insertBoard(@Param("boardTitle") String boardTitle, @Param("boardContent") String boardContent,
			@Param("boardType") String boardType);

	public int updateBoard(@Param("userNum") int userNum, @Param("boardTitle") String boardTitle, @Param("boardContent") String boardContent,
			@Param("boardType") String boardType, @Param("boardModDate") String boardModDate);
}
