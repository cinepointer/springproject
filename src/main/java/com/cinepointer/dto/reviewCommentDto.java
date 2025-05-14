package com.cinepointer.dto;

import java.util.Date;
import lombok.Data;

@Data
public class reviewCommentDto {
	private int rCommentNum;         
	private String rCommentContent;    
	private Date rCommentTime;        
	private int reviewNum;             
	private int userNum;
	private String userId;
}
