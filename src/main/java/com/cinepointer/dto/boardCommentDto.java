package com.cinepointer.dto;

import lombok.Data;

@Data
public class boardCommentDto {
	 private int commentNum;
	    private String commentContent;
	    private String commentTime;
	    private int boardNum;
	    private int userNum;

}
