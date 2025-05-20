package com.cinepointer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinepointer.dto.movieDto;
import com.cinepointer.dto.reviewDto;
import com.cinepointer.service.movieService;
import com.cinepointer.service.reviewService;

import jakarta.servlet.http.HttpSession;

@Controller
public class movieController {

    private final movieService movieService;
    private final reviewService reviewService; // 추가

    @Autowired
    public movieController(movieService movieService,reviewService reviewService) { // 생성자 추가
        this.movieService = movieService;
        this.reviewService = reviewService; // 추가
    }

    // 메인 페이지
    @GetMapping({"/", "/movies"})
    public String mainPage(
        @RequestParam(required = false, value="search") String search,
        @RequestParam(required = false, value="genre") String genre,
        @RequestParam(required = false, value="sort") String sort,
        Model model
    ) {
        List<movieDto> movies = movieService.searchMovies(search, genre, sort, null);
        model.addAttribute("movies", movies);
        model.addAttribute("popularMovies", movieService.findPopular(5));
        model.addAttribute("latestMovies", movieService.findLatest(8));
        model.addAttribute("actionMovies", movieService.findByGenre("액션", 4));
        model.addAttribute("romanceMovies", movieService.findByGenre("로맨스", 4));
        model.addAttribute("recommendedMovies", movieService.findPopular(4));
        model.addAttribute("pageType", "main");
        return "mainpage";
    }

    // 영화 상세 페이지
    @GetMapping("/movies/{id}")
    public String movieDetail(@PathVariable("id") Long id, Model model, HttpSession session) {
        movieDto movie = movieService.findById(id);
        model.addAttribute("movie", movie);

        // 관련 영화 (같은 장르 4개, 자기 자신 제외)
        List<movieDto> related = movieService.findByGenre(movie.getGenre(), 4);
        related.removeIf(m -> m.getId().equals(id));
        model.addAttribute("relatedMovies", related);

        // 로그인 여부
        Integer userNum = (Integer) session.getAttribute("userNum");
        boolean isLogin = (userNum != null);
        model.addAttribute("isLogin", isLogin);

        // 찜 여부 및 userNum 전달
        boolean isWished = false;
        if (isLogin) {
            isWished = movieService.isWished(userNum, id.intValue());
            model.addAttribute("userNum", userNum);
        }
        model.addAttribute("isWished", isWished);
        // 리뷰 미리보기 3개 추가
        List<reviewDto> recentReviews = reviewService.getLimitedReviewsByMovie(id.intValue(), 3);
        model.addAttribute("reviews", recentReviews);

        return "moviePage";
    }

    // 찜하기 (Wish 추가)
    @PostMapping("/movie/wish")
    public String wishMovie(@RequestParam("movieNum") int movieNum, HttpSession session) {
        Integer userNum = (Integer) session.getAttribute("userNum");
        if (userNum != null) {
            movieService.addWish(userNum, movieNum);
        }
        return "redirect:/movies/" + movieNum;
    }

    // 찜 취소
    @PostMapping("/movie/unwish")
    public String unwishMovie(@RequestParam("movieNum") int movieNum, HttpSession session) {
        Integer userNum = (Integer) session.getAttribute("userNum");
        if (userNum != null) {
            movieService.removeWish(userNum, movieNum);
        }
        return "redirect:/movies/" + movieNum;
    }

    // 내 찜 목록
    @GetMapping("/my-wishlist")
    public String myWishList(Model model, HttpSession session) {
        Integer userNum = (Integer) session.getAttribute("userNum");
        if (userNum != null) {
            List<movieDto> wishList = movieService.getWishList(userNum);
            model.addAttribute("wishList", wishList);
        }
        return "myWishList";
    }

    // (좋아요 기능은 서비스에 없으므로 주석 처리)
    /*
    @PostMapping("/movies/{id}/like")
    @ResponseBody
    public String likeMovie(@PathVariable("id") Long id, HttpSession session) {
        // likesService.addLike 등 구현 필요
        return "fail";
    }
    */
}
