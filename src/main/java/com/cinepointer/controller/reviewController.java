package com.cinepointer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinepointer.dto.reviewCommentDto;
import com.cinepointer.dto.reviewDto;
import com.cinepointer.service.reviewCommentService;
import com.cinepointer.service.reviewService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/review")
public class reviewController {
    private final reviewService reviewService;
    private final reviewCommentService reviewCommentService;

    public reviewController(reviewService reviewService, reviewCommentService reviewCommentService) {
        this.reviewService = reviewService;
        this.reviewCommentService = reviewCommentService;
    }

    @GetMapping("/reviews/{movieNum}")
    public String showReviewList(@PathVariable("movieNum") int movieNum, Model model) {
        model.addAttribute("reviews", reviewService.getReviewsByMovie(movieNum));
        model.addAttribute("movieNum", movieNum);
        return "reviewListPage";
    }

    @GetMapping("/form")
    public String showReviewForm(@RequestParam("movieNum") int movieNum, Model model) {
        model.addAttribute("reviewDto", new reviewDto());
        model.addAttribute("mov                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             ieNum", movieNum);
        return "reviewForm";
    }

    @PostMapping("/insert")
    public String insertReview(@ModelAttribute reviewDto dto, HttpSession session) {
        Integer userNum = (Integer) session.getAttribute("userNum");
        if (userNum == null) {
            return "redirect:/signin";
        }
        dto.setUserNum(userNum);
        reviewService.insertReview(dto);
        return "redirect:/review/reviews/" + dto.getMovieNum();
    }

    @GetMapping("/edit")
    public String showEditReviewForm(@RequestParam("reviewNum") int reviewNum, @RequestParam("movieNum") int movieNum, Model model) {
        reviewDto review = reviewService.getReviewByNum(reviewNum);
        model.addAttribute("reviewDto", review);
        model.addAttribute("movieNum", movieNum);
        return "reviewForm";
    }

    @PostMapping("/update")
    public String updateReview(@ModelAttribute reviewDto dto, HttpSession session) {
        Integer userNum = (Integer) session.getAttribute("userNum");
        if (userNum == null) {
            return "redirect:/signin";
        }
        dto.setUserNum(userNum);
        reviewService.updateReview(dto);
        return "redirect:/review/reviews/" + dto.getMovieNum();
    }

    @GetMapping("/delete")
    public String deleteReview(@RequestParam("reviewNum") int reviewNum, @RequestParam("movieNum") int movieNum, HttpSession session) {
        Integer userNum = (Integer) session.getAttribute("userNum");
        if (userNum == null) {
            return "redirect:/signin";
        }
        reviewService.deleteReview(reviewNum);
        return "redirect:/review/reviews/" + movieNum;
    }

    @PostMapping("/comment/insert")
    public String insertComment(@ModelAttribute reviewCommentDto dto, @RequestParam("movieNum") int movieNum, HttpSession session) {
        Integer userNum = (Integer) session.getAttribute("userNum");
        if (userNum == null) {
            return "redirect:/signin";
        }
        dto.setUserNum(userNum);
        reviewCommentService.insertComment(dto);
        return "redirect:/review/reviews/" + movieNum;
    }

    @PostMapping("/comment/update")
    public String updateComment(@ModelAttribute reviewCommentDto dto, @RequestParam("movieNum") int movieNum, HttpSession session) {
        Integer userNum = (Integer) session.getAttribute("userNum");
        if (userNum == null) {
            return "redirect:/signin";
        }
        dto.setUserNum(userNum);
        reviewCommentService.updateComment(dto);
        return "redirect:/review/reviews/" + movieNum;
    }

    @GetMapping("/comment/delete")
    public String deleteComment(@RequestParam("rCommentNum") int rCommentNum, @RequestParam("movieNum") int movieNum, HttpSession session) {
        Integer userNum = (Integer) session.getAttribute("userNum");
        if (userNum == null) {
            return "redirect:/signin";
        }
        reviewCommentService.deleteComment(rCommentNum);
        return "redirect:/review/reviews/" + movieNum;
    }
}
