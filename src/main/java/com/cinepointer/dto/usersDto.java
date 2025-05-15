package com.cinepointer.dto;

import lombok.Data;

@Data
public class usersDto {
    private int userNum;
    private String userId;
    private String userPasswd;
    private String userName;
    private String userEmail;
    private String roleName;
    private String userLandCd;
    private String userBirthDate;
    private int userEnabled;
}