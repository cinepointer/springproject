package com.cinepointer.controller;

import com.cinepointer.service.movieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movie")
public class movieController {
    private final movieService service;

    public movieController(movieService service) {
        this.service = service;
    }

    @GetMapping("/detail/{movieNum}")
    public String movieDetail(@PathVariable int movieNum, Model model) {
        model.addAttribute("movie", service.getMovieDetail(movieNum));
        return "moviepage";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        model.addAttribute("movies", service.searchMovies(keyword));
        return "mainpage";
    }

    @GetMapping("/adult")
    public String moviesByAdult(@RequestParam boolean movieAdult, Model model) {
        model.addAttribute("movies", service.getMoviesByAdult(movieAdult));
        return "mainpage";
    }

    @GetMapping("/list")
    public String allMovies(Model model) {
        model.addAttribute("movies", service.getAllMovies());
        return "mainpage";
    }
    
}
