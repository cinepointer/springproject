package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
	
	void insertMovieGenre(@Param("movie_genre")genreDto movie_genre);
	
	void insertActorMovie(@Param("actor_movie")tmdbActorDto actor_movie,
			@Param("movieNum")int movidNum);
	
	void insertActor(@Param("actor")tmdbActorDto actor);
	void insertMovie(@Param("movie")tmdbMovieDto movie);
}
