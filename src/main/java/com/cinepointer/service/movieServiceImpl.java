package com.cinepointer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cinepointer.dao.movieDao;
import com.cinepointer.dto.actorDto;
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

    // --- 찜 기능 ---
    @Override
    public List<movieDto> getWishList(int userNum) {
        return movieDao.selectWishListByUserNum(userNum);
    }

    @Override
    public boolean addWish(int userNum, int movieNum) {
        if (movieDao.countWish(userNum, movieNum) > 0) {
            return false; // 이미 찜함
        }
        return movieDao.insertWish(userNum, movieNum) > 0;
    }

    @Override
    public boolean isWished(int userNum, int movieNum) {
        return movieDao.countWish(userNum, movieNum) > 0;
    }

    // 오버로딩: String, Long 타입도 지원 (예외처리 추가)
    @Override
    public boolean isWished(String userNum, Long movieNum) {
        try {
            int userNumInt = Integer.parseInt(userNum);
            int movieNumInt = (movieNum != null) ? movieNum.intValue() : 0;
            return isWished(userNumInt, movieNumInt);
        } catch (NumberFormatException | NullPointerException e) {
            // 잘못된 파라미터일 경우 false 반환 (또는 상황에 따라 예외 throw 가능)
            // 필요하다면 로그 추가
            // log.warn("isWished 파라미터 오류: userNum={}, movieNum={}", userNum, movieNum);
            return false;
        }
    }

    @Override
    public boolean removeWish(int userNum, int movieNum) {
        return movieDao.deleteWish(userNum, movieNum) > 0;
    }

    // 영화 등록
    @Override
    public void insert(movieDto movie) {
        movieDao.insert(movie);
    }
    @Override
    public List<actorDto> getActorsByMovieNum(int movieNum) {
        return movieDao.findActorsByMovieNum(movieNum);
    }
}