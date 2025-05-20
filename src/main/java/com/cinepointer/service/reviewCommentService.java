package com.cinepointer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cinepointer.dao.reviewCommentDao;
import com.cinepointer.dto.reviewCommentDto;

@Service
public class reviewCommentService {
    private final reviewCommentDao commentDao;

    public reviewCommentService(reviewCommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public void insertComment(reviewCommentDto dto) {
        commentDao.insertComment(dto);
    }
    
    public void updateComment(reviewCommentDto dto) {
        commentDao.updateComment(dto);
    }

    public void deleteComment(int rCommentNum) {
        commentDao.deleteComment(rCommentNum);
    }
    
    public List<reviewCommentDto> getCommentsByReview(int reviewNum) {
        return commentDao.selectComment(reviewNum);
    }
    
    public void deleteAllByReviewNum(int reviewNum) {
        commentDao.deleteAllByReviewNum(reviewNum);
    }
    
}
