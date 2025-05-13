package com.cinepointer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinepointer.dao.boardCommentDao;
import com.cinepointer.dto.boardCommentDto;

@Service
public class boardCommentService {

    @Autowired
    private boardCommentDao boardCommentDao;

    public List<boardCommentDto> getCommentsByBoardNum(int boardNum) {
        return boardCommentDao.getCommentsByBoardNum(boardNum);
    }

    public int insertComment(boardCommentDto comment) {
        return boardCommentDao.insertComment(comment);
    }

    public int updateComment(boardCommentDto comment) {
        return boardCommentDao.updateComment(comment);
    }

    public int deleteComment(int commentNum) {
        return boardCommentDao.deleteComment(commentNum);
    }
}
