package com.cinepointer.dao;

import java.util.List;
import com.cinepointer.dto.movieDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface movieDao {
    // 전체 영화 목록
    List<movieDto> findAll();

    // 영화 상세
    movieDto findByMovieNum(int movieNum);

    // 제목 검색
    List<movieDto> searchByTitle(String keyword);

    // 성인/비성인 영화
    List<movieDto> findByAdult(boolean movieAdult);

    // 영화 등록
    void insertMovie(movieDto movie);

    // 영화 수정
    void updateMovie(movieDto movie);

    // 영화 삭제
    void deleteMovie(int movieNum);

}
