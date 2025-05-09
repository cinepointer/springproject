package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cinepointer.dto.tmdbGenreDto;


@Mapper
public interface tmdbDao {
	public void insertGenre(List<tmdbGenreDto> genres);
}
