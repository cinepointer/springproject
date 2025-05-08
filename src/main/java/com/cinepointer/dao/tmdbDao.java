package com.cinepointer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cinepointer.dto.genreDto;


@Mapper
public interface tmdbDao {
	public List<genreDto> a();
}
