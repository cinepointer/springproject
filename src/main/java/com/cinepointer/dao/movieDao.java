package com.cinepointer.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cinepointer.dto.movieDto;

@Mapper
public interface movieDao {
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

    // --- 찜 기능 ---
    // 유저의 찜 목록
    List<movieDto> selectWishListByUserNum(@Param("userNum") int userNum);

    // 찜 여부 확인
    int countWish(@Param("userNum") int userNum, @Param("movieNum") int movieNum);

    // 찜하기
    int insertWish(@Param("userNum") int userNum, @Param("movieNum") int movieNum);

    // 찜 취소
    int deleteWish(@Param("userNum") int userNum, @Param("movieNum") int movieNum);

    // --- 영화 등록 ---
    int insert(movieDto movie); // ★ 추가됨
}
