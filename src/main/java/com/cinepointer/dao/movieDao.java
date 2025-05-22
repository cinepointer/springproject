package com.cinepointer.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cinepointer.dto.movieDto;
import com.cinepointer.dto.actorMovieDto;
import com.cinepointer.dto.actorDto; // ★ 추가

@Mapper
public interface movieDao {
    // ===== 영화 검색 및 조회 =====

    // 영화 검색
    List<movieDto> searchMovies(
        @Param("search") String search,
        @Param("genre") String genre,
        @Param("sort") String sort,
        @Param("limit") Integer limit
    );

    // ID로 영화 찾기
    movieDto findById(@Param("id") Long id);

    // 장르별 영화
    List<movieDto> findByGenre(@Param("genre") String genre, @Param("limit") Integer limit);

    // 인기 영화
    List<movieDto> findPopular(@Param("limit") Integer limit);

    // 최신 영화
    List<movieDto> findLatest(@Param("limit") Integer limit);

    // ===== 찜 기능 =====

    // 유저의 찜 목록
    List<movieDto> selectWishListByUserNum(@Param("userNum") int userNum);

    // 찜 여부 확인
    int countWish(@Param("userNum") int userNum, @Param("movieNum") int movieNum);

    // 찜하기
    int insertWish(@Param("userNum") int userNum, @Param("movieNum") int movieNum);

    // 찜 취소
    int deleteWish(@Param("userNum") int userNum, @Param("movieNum") int movieNum);

    // ===== 영화 등록 =====

    // 영화 등록
    int insert(movieDto movie);

    // ===== 배우-영화 정보(출연진) =====

    /**
     * 해당 영화의 출연진(배우) 목록 반환 (actorMovieDto)
     */
    List<actorMovieDto> getActorsByMovieNum(@Param("movieNum") int movieNum);

    /**
     * 해당 영화의 출연진(배우) 정보 반환 (actorDto)
     */
    List<actorDto> findActorsByMovieNum(@Param("movieNum") int movieNum); // ★ 추가
}
