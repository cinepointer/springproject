package com.cinepointer.dto;

public class UsersDto {
    private String userId;
    private String userPasswd;
    private String userName;
    private String userEmail;
    private String userStatus;
    private String userLandCd;
    private String userBirthDate;

    // 기본 생성자
    public UsersDto() {}

    // 전체 필드 생성자
    public UsersDto(String userId, String userPasswd, String userName, String userEmail,
                    String userStatus, String userLandCd, String userBirthDate) {
        this.userId = userId;
        this.userPasswd = userPasswd;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userStatus = userStatus;
        this.userLandCd = userLandCd;
        this.userBirthDate = userBirthDate;
    }

    // Getter & Setter
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserPasswd() { return userPasswd; }
    public void setUserPasswd(String userPasswd) { this.userPasswd = userPasswd; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserStatus() { return userStatus; }
    public void setUserStatus(String userStatus) { this.userStatus = userStatus; }

    public String getUserLandCd() { return userLandCd; }
    public void setUserLandCd(String userLandCd) { this.userLandCd = userLandCd; }

    public String getUserBirthDate() { return userBirthDate; }
    public void setUserBirthDate(String userBirthDate) { this.userBirthDate = userBirthDate; }
}
