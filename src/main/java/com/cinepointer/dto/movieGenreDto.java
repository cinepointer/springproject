package com.cinepointer.dto;

import lombok.Data;
//장르 테이블에 값을 넣기 위한 dto
@Data
public class movieGenreDto {
	private int genreNum;
    private String genreName;
}
