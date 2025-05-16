package com.cinepointer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cinepointer.dao.reviewDao;
import com.cinepointer.dto.reviewCommentDto;
import com.cinepointer.dto.reviewDto;

@Service
public class reviewService {
    private final reviewDao reviewDao;
    private final reviewCommentService reviewCommentService;

    public reviewService(reviewDao reviewDao, reviewCommentService reviewCommentService) {
        this.reviewDao = reviewDao;
        this.reviewCommentService = reviewCommentService;
    }

    public List<reviewDto> getReviewsByMovie(int movieNum) {
        List<reviewDto> reviews = reviewDao.selectReview(movieNum);
        for (reviewDto review : reviews) {
            List<reviewCommentDto> comments = reviewCommentService.getCommentsByReview(review.getReviewNum());
            review.setComments(comments);
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
    
    public reviewDto getReviewWithCommentsByNum(int reviewNum) {
        reviewDto review = reviewDao.selectReviewByNum(reviewNum);
        List<reviewCommentDto> comments = reviewCommentService.getCommentsByReview(reviewNum);
        review.setComments(comments);
        return review;
    }
}
