package com.cinepointer.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinepointer.dao.movieDao;
import com.cinepointer.dto.movieDto;

@Service
@Transactional(readOnly = true)
public class movieService {

    private final movieDao movieDao;

    public movieService(movieDao movieDao) {
        this.movieDao = movieDao;
    }

    // 전체 영화 목록
    public List<movieDto> getAllMovies() {
        return movieDao.findAll();
    }

    // 영화 상세
    public movieDto getMovieDetail(int movieNum) {
        return movieDao.findByMovieNum(movieNum);
    }

    // 제목 검색
    public List<movieDto> searchMovies(String keyword) {
        return movieDao.searchByTitle(keyword);
    }

    // 성인/비성인 영화
    public List<movieDto> getMoviesByAdult(boolean movieAdult) {
        return movieDao.findByAdult(movieAdult);
    }

    @Transactional
    public void insertMovie(movieDto movie) {
        movieDao.insertMovie(movie);
    }

    @Transactional
    public void updateMovie(movieDto movie) {
        movieDao.updateMovie(movie);
    }

    @Transactional
    public void deleteMovie(int movieNum) {
        movieDao.deleteMovie(movieNum);
    	}
	}
   
