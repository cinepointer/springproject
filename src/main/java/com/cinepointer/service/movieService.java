package com.cinepointer.service;

import java.util.List;
import com.cinepointer.dto.movieDto;

public interface movieService {
    List<movieDto> searchMovies(String search, String genre, String sort, Integer limit);
    movieDto findById(Long id);
    List<movieDto> findByGenre(String genre, Integer limit);
    List<movieDto> findLatest(Integer limit);
    List<movieDto> findPopular(Integer limit);
    List<movieDto> getWishList(String userId);

    // 찜하기 기능 추가
    boolean addWish(String userId, Long movieId);
    boolean isWished(String userId, Long movieId);
}
