package com.cinepointer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class tmdbGenreDto {

    @JsonProperty("id")
    private int genreNum;

    @JsonProperty("name")
    private String genreName;
}
