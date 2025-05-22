package com.cinepointer.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class movieDto {
    private Long id;
    private String title;
    private String genre;
    private String description;
    private Date releaseDate;
    private Integer likeCount;
    private List<String> actors;
    private String posterPath;
    // ⭐️ 찜(위시리스트) 여부 필드
    private Boolean wished;
}
