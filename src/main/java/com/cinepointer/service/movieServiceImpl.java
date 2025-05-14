package com.cinepointer.service;

import com.cinepointer.dao.movieDao;
import com.cinepointer.dto.movieDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class movieServiceImpl implements movieService {

    private final movieDao movieDao;

    public movieServiceImpl(movieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<movieDto> searchMovies(String search, String genre, String sort, Integer limit) {
        return movieDao.searchMovies(search, genre, sort, limit);
    }

    @Override
    public movieDto findById(Long id) {
        return movieDao.findById(id);
    }

    @Override
    public List<movieDto> findByGenre(String genre, Integer limit) {
        return movieDao.findByGenre(genre, limit);
    }

    @Override
    public List<movieDto> findLatest(Integer limit) {
        return movieDao.findLatest(limit);
    }

    @Override
    public List<movieDto> findPopular(Integer limit) {
        return movieDao.findPopular(limit);
    }

    @Override
    public void insert(movieDto movie) {
        movieDao.insert(movie);
    }
}
