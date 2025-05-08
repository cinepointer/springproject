package com.cinepointer.dto;

import lombok.Data;

@Data
public class boardDto {
	private int boardNum;
    private String boardTitle;
    private String boardContent;
    private int boardViewCnt;
    private String boardRegDate;
    private String boardModDate;
    private String boardType;
    private int userNum;

}
