package com.cinepointer.service;

import java.util.List;
import com.cinepointer.dto.movieDto;

public interface movieService {
    List<movieDto> searchMovies(String search, String genre, String sort, Integer limit);
    movieDto findById(Long id);
    List<movieDto> findByGenre(String genre, Integer limit);
    List<movieDto> findLatest(Integer limit);
    List<movieDto> findPopular(Integer limit);

    // 찜 기능
    List<movieDto> getWishList(int userNum);                 // 내 찜 목록
    boolean addWish(int userNum, int movieNum);              // 찜하기
    boolean isWished(int userNum, int movieNum);             // 찜 여부 (기존)
    boolean isWished(String userNum, Long movieNum);         // 찜 여부 (오버로딩 추가)
    boolean removeWish(int userNum, int movieNum);           // 찜 취소

    // 영화 등록
    void insert(movieDto movie);
}
