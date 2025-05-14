package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cinepointer.dto.reviewDto;

@Mapper
public interface reviewDao {
	void insertReview(reviewDto dto);
	
    List<reviewDto> selectReview(int movieNum);
    
    void updateReview(reviewDto dto);
    
    void deleteReview(int reviewNum);
    
    reviewDto selectReviewByNum(int reviewNum);
}
