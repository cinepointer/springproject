package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cinepointer.dto.boardDto;
import com.cinepointer.dto.reviewDto;

public interface reviewDao {
	public List<reviewDto> getList();

	public int insertReview(boardDto dto);

	public int updateReview(@Param("userNum") int userNum, @Param("boardTitle") String boardTitle, @Param("boardContent") String boardContent,
			@Param("boardType") String boardType, @Param("boardModDate") String boardModDate);
}
