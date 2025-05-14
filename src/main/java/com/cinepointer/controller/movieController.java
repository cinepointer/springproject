package com.cinepointer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinepointer.dto.movieDto;
import com.cinepointer.dto.usersDto;
import com.cinepointer.service.movieService;

import jakarta.servlet.http.HttpSession;

@Controller
public class movieController {

    private final movieService movieService;

    @Autowired
    public movieController(movieService movieService) {
        this.movieService = movieService;
    }

    // 메인 페이지
    @GetMapping({"/", "/movies"})
    public String mainPage(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String genre,
        @RequestParam(required = false) String sort,
        Model model) {

        List<movieDto> movies = movieService.searchMovies(search, genre, sort, null);
        model.addAttribute("movies", movies);

        model.addAttribute("popularMovies", movieService.findPopular(5));
        model.addAttribute("latestMovies", movieService.findLatest(8));
        model.addAttribute("actionMovies", movieService.findByGenre("액션", 4));
        model.addAttribute("romanceMovies", movieService.findByGenre("로맨스", 4));
        model.addAttribute("recommendMovies", movieService.findPopular(4));

        return "mainpage";
    }

    // 영화 상세 페이지
    @GetMapping("/movies/{id}")
    public String movieDetail(@PathVariable Long id, Model model, HttpSession session) {
        movieDto movie = movieService.findById(id);
        model.addAttribute("movie", movie);

        // 관련 영화 (예: 같은 장르 4개)
        List<movieDto> related = movieService.findByGenre(movie.getGenre(), 4);
        // 자기 자신은 제외
        related.removeIf(m -> m.getId().equals(id));
        model.addAttribute("relatedMovies", related);

        // 로그인 여부
        Boolean isLogin = session.getAttribute("user") != null;
        model.addAttribute("isLogin", isLogin);

        return "moviepage";
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "signIn";
    }

    // 회원가입 폼
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new usersDto());
        return "signUp";
    }
   
  


    
}
