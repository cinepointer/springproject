package com.cinepointer.dto;

import java.util.List;

import lombok.Data;

@Data
public class reviewDto {
	private int reviewNum;
	private String reviewTitle;
	private String reviewContent;
	private double reviewRating;
	private String reviewTime;
	private int userNum;
	private int movieNum;
	private String userId; 
	private List<reviewCommentDto> comments;
	private String movieTitle;
}
