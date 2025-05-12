package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cinepointer.dto.actorMovieDto;
import com.cinepointer.dto.genreDto;
import com.cinepointer.dto.tmdbActorDto;
import com.cinepointer.dto.tmdbGenreDto;
import com.cinepointer.dto.tmdbMovieDto;


@Mapper
public interface tmdbDao {
	//장르 추가
	void insertGenre(@Param("genres") List<tmdbGenreDto> genres);
	//장르 번호 가져오기
	List<Integer> selectGenre();
	
	// 배치 삽입 - 영화 리스트
    void insertMoviesBatch(@Param("movieList") List<tmdbMovieDto> movieList);

    // 배치 삽입 - 배우 리스트
    void insertActorsBatch(@Param("actorList") List<tmdbActorDto> actorList);

    // 배치 삽입 - 장르 리스트
    void insertMovieGenresBatch(@Param("movieGenreList") List<genreDto> movieGenreList);

    // 배치 삽입 - 배우-영화 관계 리스트
    void insertActorMoviesBatch(@Param("actorMovieList") List<actorMovieDto> actorMovieList);


}
