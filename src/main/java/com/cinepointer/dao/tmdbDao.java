package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cinepointer.dto.tmdbGenreDto;


@Mapper
public interface tmdbDao {
	void insertGenre(@Param("genres") List<tmdbGenreDto> genres);
}
