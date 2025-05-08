package com.cinepointer.dto;

import java.util.List;

import lombok.Data;

@Data
public class tmdbDto {
	private int id; // TMDB 영화 ID
    private String title;
    private String releaseDate;
    private String posterPath;
    private String overview;
    private boolean adult;
    private List<Integer> genreIds; // TMDB 장르 번호 리스트
}
