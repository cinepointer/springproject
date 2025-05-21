package com.cinepointer.dto;


import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;


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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate userBirthDate;
    private int userEnabled;
 
 
}
