package com.cinepointer.dto;

import lombok.Data;

@Data
public class movie2Dto {
    private int movieNum;
    private String movieTitle;
    private String movieOverview;
    private String moviePosterPath;    // 포스터 URL 필드 추가

}
