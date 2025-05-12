package com.cinepointer.dto;

import java.util.List;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@Data
public class tmdbMovieDto {
	@JsonProperty("id")
    private int movieNum;

    @JsonProperty("title")
    private String movieTitle;

    @JsonProperty("release_date")
    private Date movieReleaseDate;

    @JsonProperty("poster_path")
    private String moviePosterPath;

    @JsonProperty("overview")
    private String movieOverview;
    
    @JsonProperty("adult")
    private boolean movieAdult;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
}
