package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cinepointer.dto.boardDto;

@Mapper
public interface boardDao {
	public List<boardDto> getList();

	public int insertBoard(boardDto dto);
	
	public boardDto getBoardDetail(@Param("boardNum") int boardNum);

	public int updateBoard(boardDto dto);

	public int deleteBoard(@Param("boardNum") int boardNum);

	public int viewCnt(@Param("boardNum") int boardNum);
}
