package com.cinepointer.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class movieDto {
	 private int movieNum;
    private String movieTitle;
    private Date movieReleaseDate;
    private String moviePosterPath;
    private String movieOverview;
    private Date movieRegDate;
    private int movieCheck;
    private boolean movieAdult;
}
