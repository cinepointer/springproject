package com.cinepointer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cinepointer.dto.boardCommentDto;
import com.cinepointer.dto.boardDto;
import com.cinepointer.dto.usersDto;
import com.cinepointer.service.boardCommentService;
import com.cinepointer.service.boardService;
import com.cinepointer.service.userService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final boardService boardService;
    private final boardCommentService boardCommentService;
    private final userService userService;

    public BoardController(boardService boardService, boardCommentService boardCommentService, userService userService) {
        this.boardService = boardService;
        this.boardCommentService = boardCommentService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("boardList", boardService.getList());
        return "boardListPage";
    }

    @GetMapping("/insertForm")
    public String insertForm(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";

        model.addAttribute("userId", userId);
        model.addAttribute("boardDto", new boardDto());
        return "boardInsert";
    }

    @PostMapping("/insert")
    public String insertBoard(boardDto dto, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";

        usersDto user = userService.findById(userId);
        dto.setUserNum(user.getUserNum());
        boardService.insertBoard(dto);
        return "redirect:/board/list";
    }

    @GetMapping("/detail/{boardNum}")
    public String boardDetail(@PathVariable int boardNum, Model model, HttpSession session) {
        String viewKey = "viewed_board_" + boardNum;

        if (session.getAttribute(viewKey) == null) {
            boardService.boardViewCnt(boardNum);
            session.setAttribute(viewKey, true);
        }

        model.addAttribute("board", boardService.getBoardDetail(boardNum));
        model.addAttribute("comment", boardCommentService.getCommentsByBoardNum(boardNum));
        return "boardDetail";
    }

    @GetMapping("/updateForm/{boardNum}")
    public String updateForm(@PathVariable int boardNum, Model model) {
        model.addAttribute("board", boardService.getBoardDetail(boardNum));
        return "boardUpdate";
    }

    @PostMapping("/update")
    public String boardUpdate(boardDto dto, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";

        usersDto user = userService.findById(userId);
        boardDto original = boardService.getBoardDetail(dto.getBoardNum());

        if (user == null || user.getUserNum() != original.getUserNum()) {
            return "redirect:/board/list?error=unauthorized";
        }

        boardService.updateBoard(dto);
        return "redirect:/board/list";
    }

    @GetMapping("/delete/{boardNum}")
    public String boardDelete(@PathVariable int boardNum, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";

        usersDto user = userService.findById(userId);
        boardDto board = boardService.getBoardDetail(boardNum);

        if (user == null || board.getUserNum() != user.getUserNum()) {
            return "redirect:/board/list?error=unauthorized";
        }

        boardService.deleteBoard(boardNum);
        return "redirect:/board/list";
    }

    @PostMapping("/comment")
    public String insertComment(@RequestParam int boardNum, @ModelAttribute boardCommentDto comment, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";

        usersDto user = userService.findById(userId);
        comment.setBoardNum(boardNum);
        comment.setUserNum(user.getUserNum());
        boardCommentService.insertComment(comment);
        return "redirect:/board/detail/" + boardNum;
    }

    @PostMapping("/commentEdit")
    public String updateComment(@RequestParam int commentNum, @RequestParam int boardNum,
                                 @ModelAttribute boardCommentDto comment, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";

        usersDto user = userService.findById(userId);
        boardCommentDto existing = boardCommentService.getCommentByNum(commentNum);

        if (existing == null || existing.getUserNum() != user.getUserNum()) {
            return "redirect:/board/detail/" + boardNum + "?error=unauthorized";
        }

        comment.setCommentNum(commentNum);
        boardCommentService.updateComment(comment);
        return "redirect:/board/detail/" + boardNum;
    }

    @GetMapping("/commentDelete/{commentNum}/{boardNum}")
    public String deleteComment(@PathVariable int commentNum, @PathVariable int boardNum, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";

        usersDto user = userService.findById(userId);
        boardCommentDto comment = boardCommentService.getCommentByNum(commentNum);

        if (comment == null || comment.getUserNum() != user.getUserNum()) {
            return "redirect:/board/detail/" + boardNum + "?error=unauthorized";
        }

        boardCommentService.deleteComment(commentNum);
        return "redirect:/board/detail/" + boardNum;
    }
}
