package com.cinepointer.service;

import java.util.List;

import com.cinepointer.dto.tmdbGenreDto;
import com.cinepointer.dto.tmdbMovieDto;

public interface tmdbService {
	List<tmdbMovieDto> insertPopularMovies();
	List<tmdbGenreDto> insertGenre();
	List<tmdbMovieDto> updatePopularMovies();
}
