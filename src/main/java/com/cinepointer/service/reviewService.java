package com.cinepointer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cinepointer.dao.reviewDao;
import com.cinepointer.dto.reviewDto;

@Service
public class reviewService {
    private final reviewDao reviewDao;

    public reviewService(reviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    public List<reviewDto> getReviewsByMovie(int movieNum) {
        List<reviewDto> reviews = reviewDao.selectReview(movieNum);
        for (reviewDto review : reviews) {
            review.setComments(reviewDao.selectComment(review.getReviewNum()));
        }
        return reviews;
    }

    public void insertReview(reviewDto dto) {
        reviewDao.insertReview(dto);
    }

    public void updateReview(reviewDto dto) {
        reviewDao.updateReview(dto);
    }

    public void deleteReview(int reviewNum) {
        reviewDao.deleteReview(reviewNum);
    }

    public reviewDto getReviewByNum(int reviewNum) {
        return reviewDao.selectReviewByNum(reviewNum);
    }
}
