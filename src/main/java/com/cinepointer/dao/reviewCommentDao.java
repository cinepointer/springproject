package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cinepointer.dto.reviewCommentDto;

@Mapper
public interface reviewCommentDao {
	void insertComment(reviewCommentDto dto);
	
    List<reviewCommentDto> selectComment(int reviewNum);
    
    void updateComment(reviewCommentDto dto);
    
    void deleteComment(int rCommentNum);
}
