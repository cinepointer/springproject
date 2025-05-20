package com.cinepointer.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cinepointer.dao.movieDao;
import com.cinepointer.dto.movieDto;

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
    public List<movieDto> getWishList(String userId) {
        return movieDao.selectWishListByUserId(userId);
    }

    // 찜하기 기능
    @Override
    public boolean addWish(String userId, Long movieId) {
        // 이미 찜했는지 확인
        if (movieDao.countWish(userId, movieId) > 0) {
            return false;
        }
        return movieDao.insertWish(userId, movieId) > 0;
    }

    @Override
    public boolean isWished(String userId, Long movieId) {
        return movieDao.countWish(userId, movieId) > 0;
    }

    // 영화 등록
    @Override
    public void insert(movieDto movie) {
        movieDao.insert(movie);
    }
}