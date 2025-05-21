package com.cinepointer.service;

import java.util.List;
import com.cinepointer.dto.movieDto;
import com.cinepointer.dto.actorMovieDto;
import com.cinepointer.dto.actorDto; // ★ 추가

public interface movieService {
    List<movieDto> searchMovies(String search, String genre, String sort, Integer limit);
    movieDto findById(Long id);
    List<movieDto> findByGenre(String genre, Integer limit);
    List<movieDto> findLatest(Integer limit);
    List<movieDto> findPopular(Integer limit);

    // 찜 기능
    List<movieDto> getWishList(int userNum);
    boolean addWish(int userNum, int movieNum);
    boolean isWished(int userNum, int movieNum);
    boolean isWished(String userNum, Long movieNum);
    boolean removeWish(int userNum, int movieNum);

    // 영화 등록
    void insert(movieDto movie);

    // ===== 배우 정보 관련 메서드 =====
    /**
     * 해당 영화의 출연진(배우) 매핑 목록 반환
     */
    List<actorMovieDto> getActorMoviesByMovieNum(int movieNum);

    /**
     * 배우 번호 리스트로 배우 정보 조회
     */
    List<actorDto> getActorsByNums(List<Integer> actorNums); // ★ 추가
}
