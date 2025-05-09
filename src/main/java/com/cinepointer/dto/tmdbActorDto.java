package com.cinepointer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class tmdbActorDto {

    @JsonProperty("id")
    private int actorId; // 배우 고유 ID

    @JsonProperty("name")
    private String actorName; // 배우 이름

    @JsonProperty("cast_id")
    private int castOrder; // 출연 중요도

    @JsonProperty("character")
    private String characterName; // 배역 이름

    @JsonProperty("gender")
    private int gender; // 성별 (1: 여성, 2: 남성, 0: 알 수 없음)
}
