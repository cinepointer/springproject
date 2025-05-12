package com.cinepointer.dto;

import java.util.Date;

public class movieDto {
    private int movieNum;
    private String movieTitle;
    private Date movieReleaseDate;
    private String moviePosterPath;
    private String movieOverview;
    private Date movieRegDate;
    private int movieCheck;
    private boolean movieAdult;

    public int getMovieNum() { return movieNum; }
    public void setMovieNum(int movieNum) { this.movieNum = movieNum; }

    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public Date getMovieReleaseDate() { return movieReleaseDate; }
    public void setMovieReleaseDate(Date movieReleaseDate) { this.movieReleaseDate = movieReleaseDate; }

    public String getMoviePosterPath() { return moviePosterPath; }
    public void setMoviePosterPath(String moviePosterPath) { this.moviePosterPath = moviePosterPath; }

    public String getMovieOverview() { return movieOverview; }
    public void setMovieOverview(String movieOverview) { this.movieOverview = movieOverview; }

    public Date getMovieRegDate() { return movieRegDate; }
    public void setMovieRegDate(Date movieRegDate) { this.movieRegDate = movieRegDate; }

    public int getMovieCheck() { return movieCheck; }
    public void setMovieCheck(int movieCheck) { this.movieCheck = movieCheck; }

    public boolean isMovieAdult() { return movieAdult; }
    public void setMovieAdult(boolean movieAdult) { this.movieAdult = movieAdult; }
}
