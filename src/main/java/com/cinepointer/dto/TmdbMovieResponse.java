package com.cinepointer.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TmdbMovieResponse {
    @JsonProperty("results")
    private List<tmdbMovieDto> results;

    @JsonProperty("page")
    private int page;

    @JsonProperty("total_pages")
    private int totalPages;
}
