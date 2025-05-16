package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cinepointer.dto.reviewCommentDto;

@Mapper
public interface reviewCommentDao {
	void insertComment(reviewCommentDto dto);
	
    List<reviewCommentDto> selectComment(int reviewNum);
    
    void updateComment(reviewCommentDto dto);
    
    void deleteComment(int rCommentNum);
    
    void deleteAllByReviewNum(@Param("reviewNum") int reviewNum);
}
