package com.cinepointer.dto;

import java.util.Date;
import java.util.List;

public class movieDto {
    private Long id;
    private String title;
    private String genre;
    private String description;
    private Date releaseDate;
    private Integer likeCount;
    private List<String> actors;
    private String posterPath;

    // ⭐️ 찜(위시리스트) 여부 필드 추가
    private Boolean wished;

    // Getter, Setter

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getReleaseDate() { return releaseDate; }
    public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }

    public Integer getLikeCount() { return likeCount; }
    public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }

    public List<String> getActors() { return actors; }
    public void setActors(List<String> actors) { this.actors = actors; }

    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    // ⭐️ 찜(위시리스트) 여부 Getter, Setter
    public Boolean getWished() { return wished; }
    public void setWished(Boolean wished) { this.wished = wished; }
}
