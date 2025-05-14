package com.cinepointer.dto;

public class usersDto {
    private String user_id;
    private String user_passwd;
    private String user_name;
    private String user_email;
    private String user_status;
    private String user_land_cd;
    private String user_birth_date;

    // 기본 생성자
    public usersDto() {}

    // 전체 필드 생성자
    public usersDto(String user_id, String user_passwd, String user_name, String user_email,
                    String user_status, String user_land_cd, String user_birth_date) {
        this.user_id = user_id;
        this.user_passwd = user_passwd;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_status = user_status;
        this.user_land_cd = user_land_cd;
        this.user_birth_date = user_birth_date;
    }

    // Getter & Setter
    public String getUser_id() { return user_id; }
    public void setUser_id(String user_id) { this.user_id = user_id; }

    public String getUser_passwd() { return user_passwd; }
    public void setUser_passwd(String user_passwd) { this.user_passwd = user_passwd; }

    public String getUser_name() { return user_name; }
    public void setUser_name(String user_name) { this.user_name = user_name; }

    public String getUser_email() { return user_email; }
    public void setUser_email(String user_email) { this.user_email = user_email; }

    public String getUser_status() { return user_status; }
    public void setUser_status(String user_status) { this.user_status = user_status; }

    public String getUser_land_cd() { return user_land_cd; }
    public void setUser_land_cd(String user_land_cd) { this.user_land_cd = user_land_cd; }

    public String getUser_birth_date() { return user_birth_date; }
    public void setUser_birth_date(String user_birth_date) { this.user_birth_date = user_birth_date; }
}
