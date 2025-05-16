package com.cinepointer.dto;

import lombok.Data;

@Data
public class reviewCommentDto {
	private int rCommentNum;         
	private String rCommentContent;    
	private String rCommentTime;        
	private int reviewNum;             
	private int userNum;
	private String userId;
}