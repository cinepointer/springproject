package com.cinepointer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinepointer.dao.boardDao;
import com.cinepointer.dto.boardDto;

@Service
public class boardService {

    @Autowired
    private boardDao boardDao;

    public List<boardDto> getList() {
        return boardDao.getList();
    }

    public int insertBoard(boardDto dto) {
        return boardDao.insertBoard(dto);
    }

    public boardDto getBoardDetail(int boardNum) {
        boardDao.viewCnt(boardNum);
        return boardDao.getBoardDetail(boardNum);
    }

    public int updateBoard(boardDto dto) {
        return boardDao.updateBoard(dto);
    }

    public int deleteBoard(int boardNum) {
        return boardDao.deleteBoard(boardNum);
    }
    
    public void boardViewCnt(int boardNum) {
        boardDao.viewCnt(boardNum);
    }
}
