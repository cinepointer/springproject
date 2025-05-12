package com.cinepointer.dto;

import java.util.Date;

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

    // Getter, Setter
    public int getUserNum() { return userNum; }
    public void setUserNum(int userNum) { this.userNum = userNum; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserPasswd() { return userPasswd; }
    public void setUserPasswd(String userPasswd) { this.userPasswd = userPasswd; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public Date getUserRegDate() { return userRegDate; }
    public void setUserRegDate(Date userRegDate) { this.userRegDate = userRegDate; }

    public String getUserStatus() { return userStatus; }
    public void setUserStatus(String userStatus) { this.userStatus = userStatus; }

    public String getUserLangCd() { return userLangCd; }
    public void setUserLangCd(String userLangCd) { this.userLangCd = userLangCd; }

    public Date getUserBirthDate() { return userBirthDate; }
    public void setUserBirthDate(Date userBirthDate) { this.userBirthDate = userBirthDate; }
}
