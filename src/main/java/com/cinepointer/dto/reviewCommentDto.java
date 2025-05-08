package com.cinepointer.dto;

import java.util.Date;
import lombok.Data;

@Data
public class reviewCommentDto {
	private int Rcomment_num;         
	private String RcommentContent;    
	private Date RcommentTime;        
	private int reviewNum;             
	private int userNum;
}
