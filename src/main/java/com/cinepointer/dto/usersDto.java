package com.cinepointer.dto;

import java.util.Date;

import lombok.Data;

@Data
public class usersDto {
	 private int userNum;
    private String userId;
    private String userPasswd;
    private String userName;
    private String userEmail;
    private Date userRegDate;
    private String userStatus;
    private String userLangCd;
    private Date userBirthDate;
}
