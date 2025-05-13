package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cinepointer.dto.boardCommentDto;

@Mapper
public interface boardCommentDao {
	public List<boardCommentDto> getCommentsByBoardNum(@Param("boardNum") int boardNum);

	public int insertComment(boardCommentDto comment);

	public int updateComment(boardCommentDto comment);

	public int deleteComment(@Param("commentNum") int commentNum);
}