package com.cinepointer.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinepointer.dto.movieDto;
import com.cinepointer.dto.reviewCommentDto;
import com.cinepointer.dto.reviewDto;
import com.cinepointer.dto.usersDto;
import com.cinepointer.service.movieService;
import com.cinepointer.service.reviewCommentService;
import com.cinepointer.service.reviewService;
import com.cinepointer.service.userService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/review")
public class reviewController {
    private final reviewService reviewService;
    private final reviewCommentService reviewCommentService;
    private final userService userService;
    private final movieService movieService;


    public reviewController(reviewService reviewService, reviewCommentService reviewCommentService,userService userService,movieService movieService) {
        this.reviewService = reviewService;
        this.reviewCommentService = reviewCommentService;
        this.userService = userService;
        this.movieService = movieService;
    }

    @GetMapping("/reviews/{movieNum}")
    public String showReviewList(@PathVariable("movieNum") int movieNum, Model model) {
    	List<reviewDto> reviews = reviewService.getReviewsByMovie(movieNum);
        model.addAttribute("reviews", reviews);
        model.addAttribute("movieNum", movieNum);
        
        movieDto movie = movieService.findById((long)movieNum);
        model.addAttribute("movie", movie);
        return "reviewListPage";
    }
    
    @GetMapping("/view")
    public String viewReview(@RequestParam("reviewNum") int reviewNum,
                             @RequestParam("movieNum") int movieNum,
                             Model model) {
        reviewDto review = reviewService.getReviewWithCommentsByNum(reviewNum);
        model.addAttribute("review", review);
        model.addAttribute("movieNum", movieNum);
        return "reviewDetailPage";
    }


    @GetMapping("/form")
    public String showReviewForm(@RequestParam("movieNum") int movieNum, Model model, HttpSession session) {
    	String userId = (String) session.getAttribute("userId");
    	if (userId == null) return "redirect:/signin";
    	reviewDto dto = new reviewDto();
        dto.setUserId(userId);
        model.addAttribute("reviewDto", dto);
        model.addAttribute("movieNum", movieNum);
        return "reviewForm";
    }

    @PostMapping("/insert")
    public String insertReview(@ModelAttribute reviewDto dto, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";
        usersDto user = userService.findById(userId);
        if (user == null) return "redirect:/signin";
        dto.setUserNum(user.getUserNum());
        reviewService.insertReview(dto);
        return "redirect:/review/reviews/" + dto.getMovieNum();
    }

    @GetMapping("/edit")
    public String showEditReviewForm(@RequestParam("reviewNum") int reviewNum, @RequestParam("movieNum") int movieNum, Model model) {
        reviewDto review = reviewService.getReviewByNum(reviewNum);
        model.addAttribute("reviewDto", review);
        model.addAttribute("movieNum", movieNum);
        return "reviewUpdateForm";
    }

    @PostMapping("/update")
    public String updateReview(@ModelAttribute reviewDto dto, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";
        usersDto user = userService.findById(userId);
        if (user == null) return "redirect:/signin";
        dto.setUserNum(user.getUserNum());
        reviewService.updateReview(dto);
        return "redirect:/review/view?reviewNum=" + dto.getReviewNum() + "&movieNum=" + dto.getMovieNum();
    }

    @GetMapping("/delete")
    public String deleteReview(@RequestParam("reviewNum") int reviewNum, @RequestParam("movieNum") int movieNum, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";
        usersDto user = userService.findById(userId);
        if (user == null) return "redirect:/signin";
        reviewCommentService.deleteAllByReviewNum(reviewNum);
        reviewService.deleteReview(reviewNum);
        return "redirect:/review/reviews/" + movieNum;
    }

    @PostMapping("/comment/insert")
    public String insertComment(@ModelAttribute reviewCommentDto dto, @RequestParam("movieNum") int movieNum, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";
        usersDto user = userService.findById(userId);
        if(user == null) return "redirect:/signin";
        dto.setUserNum(user.getUserNum());
        reviewCommentService.insertComment(dto);
        return "redirect:/review/view?reviewNum=" + dto.getReviewNum() + "&movieNum=" + movieNum;
    }

    @PostMapping("/comment/update")
    public String updateComment(@ModelAttribute reviewCommentDto dto, @RequestParam("movieNum") int movieNum, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";
        usersDto user = userService.findById(userId);
        if (user == null) return "redirect:/signin";
        dto.setUserNum(user.getUserNum());
        reviewCommentService.updateComment(dto);
        return "redirect:/review/view?reviewNum=" + dto.getReviewNum() + "&movieNum=" + movieNum;
    }

    @GetMapping("/comment/delete")
    public String deleteComment(@RequestParam("rCommentNum") int rCommentNum, @RequestParam("movieNum") int movieNum, @RequestParam("reviewNum") int reviewNum, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/signin";
        usersDto user = userService.findById(userId);
        if (user == null) return "redirect:/signin";
        reviewCommentService.deleteComment(rCommentNum);
        return "redirect:/review/view?reviewNum=" + reviewNum + "&movieNum=" + movieNum;
    }
}
