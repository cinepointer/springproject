package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cinepointer.dto.movieDto;

@Mapper
public interface movieDao {
    List<movieDto> searchMovies(
        @Param("search") String search,
        @Param("genre") String genre,
        @Param("sort") String sort,
        @Param("limit") Integer limit
    );
    movieDto findById(@Param("id") Long id);
    List<movieDto> findByGenre(@Param("genre") String genre, @Param("limit") Integer limit);
    List<movieDto> findPopular(@Param("limit") Integer limit);
    List<movieDto> findLatest(@Param("limit") Integer limit);
    List<movieDto> selectWishListByUserId(String userId);
    void insert(movieDto movie);
    int countWish(String userId, Long movieId); // 찜 여부 확인
    int insertWish(String userId, Long movieId); // 찜하기

}
