package com.cinepointer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinepointer.dto.boardCommentDto;
import com.cinepointer.dto.boardDto;
import com.cinepointer.dto.usersDto;
import com.cinepointer.service.boardCommentService;
import com.cinepointer.service.boardService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController{

    private final boardService boardService;
    private final boardCommentService boardCommentService;

    public BoardController(boardService boardService, boardCommentService boardCommentService) {
        this.boardService = boardService;
        this.boardCommentService = boardCommentService;
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("boardList", boardService.getList());
        return "boardListPage";
    }

    @GetMapping("/insertForm")
    public String insertForm(Model model) {
        model.addAttribute("boardDto", new boardDto());
        return "boardInsert";
    }

    @PostMapping("/insert")
    public String insertBoard(boardDto dto, HttpSession session) {
    	usersDto user = (usersDto) session.getAttribute("user");
    	dto.setUserNum(user.getUserNum());
        boardService.insertBoard(dto);
        return "redirect:/board/list";
    }

    @GetMapping("/detail/{boardNum}")
    public String boardDetail(@PathVariable int boardNum, Model model) {
        boardService.boardViewCnt(boardNum);
        model.addAttribute("board", boardService.getBoardDetail(boardNum));
        model.addAttribute("comment", boardCommentService.getCommentsByBoardNum(boardNum));
        return "boardDetail";
    }

    @GetMapping("/updateForm/{boardNum}")
    public String updateForm(@PathVariable int boardNum, Model model) {
        model.addAttribute("boardDto", boardService.getBoardDetail(boardNum));
        return "boardUpdate";
    }

    @PostMapping("/update")
    public String boardUpdate(boardDto dto,HttpSession session) {
    	usersDto user = (usersDto) session.getAttribute("user");
        boardDto original = boardService.getBoardDetail(dto.getBoardNum());

        if (user == null || user.getUserNum() != original.getUserNum()) {
            return "redirect:/board/list?error=unauthorized";
        }
        boardService.updateBoard(dto);
        return "redirect:/board/list";
    }

    @GetMapping("/delete/{boardNum}")
    public String boardDelete(@PathVariable int boardNum,HttpSession session) {
    	usersDto user = (usersDto) session.getAttribute("user");
        boardDto board = boardService.getBoardDetail(boardNum);

        if (user == null || board.getUserNum() != user.getUserNum()) {
            return "redirect:/board/list?error=unauthorized";
        }
        boardService.deleteBoard(boardNum);
        return "redirect:/board/list";
    }

    @PostMapping("/comment")
    public String insertComment(@RequestParam int boardNum, @ModelAttribute boardCommentDto comment,HttpSession session) {
    	usersDto user = (usersDto) session.getAttribute("user");
        if (user == null) return "redirect:/signin";
        comment.setBoardNum(boardNum);
        comment.setUserNum(user.getUserNum());
        boardCommentService.insertComment(comment);
        return "redirect:/board/detail/" + boardNum;
    }

    @PostMapping("/commentEdit")
    public String updateComment(@RequestParam int commentNum, @RequestParam int boardNum, @ModelAttribute boardCommentDto comment,HttpSession session) {
    	usersDto user = (usersDto) session.getAttribute("user");
        boardCommentDto existing = boardCommentService.getCommentByNum(commentNum);

        if (user == null || existing == null || existing.getUserNum() != user.getUserNum()) {
            return "redirect:/board/detail/" + boardNum + "?error=unauthorized";
        }
        comment.setCommentNum(commentNum);
        boardCommentService.updateComment(comment);
        return "redirect:/board/detail/" + boardNum;
    }

    @GetMapping("/commentDelete/{commentNum}/{boardNum}")
    public String deleteComment(@PathVariable int commentNum, @PathVariable int boardNum,HttpSession session) {
    	usersDto user = (usersDto) session.getAttribute("user");
        boardCommentDto comment = boardCommentService.getCommentByNum(commentNum);

        if (user == null || comment == null || comment.getUserNum() != user.getUserNum()) {
            return "redirect:/board/detail/" + boardNum + "?error=unauthorized";
        }
        boardCommentService.deleteComment(commentNum);
        return "redirect:/board/detail/" + boardNum;
    }
}
