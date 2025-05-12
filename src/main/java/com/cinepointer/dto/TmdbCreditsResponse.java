package com.cinepointer.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TmdbCreditsResponse {
    @JsonProperty("cast")
    private List<tmdbActorDto> cast;
}
