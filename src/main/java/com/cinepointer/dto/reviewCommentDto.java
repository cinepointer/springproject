package com.cinepointer.dto;

import java.util.Date;
import lombok.Data;

@Data
public class reviewCommentDto {
	int Rcomment_num;         
   	String RcommentContent;    
   	Date RcommentTime;        
   	int reviewNum;             
    int userNum;
}
