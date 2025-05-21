package com.cinepointer.service;

import java.util.List;

import com.cinepointer.dto.tmdbGenreDto;
import com.cinepointer.dto.tmdbMovieDto;


public interface tmdbService {
	void insertPopularMovies();
	void insertGenre();
	int existsGanre();
}
