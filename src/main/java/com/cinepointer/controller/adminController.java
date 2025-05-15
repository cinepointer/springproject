package com.cinepointer.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cinepointer.dto.tmdbMovieDto;
import com.cinepointer.dto.usersDto;
import com.cinepointer.service.tmdbApiService;
import com.cinepointer.service.tmdbService;
import com.cinepointer.service.userService;



@Controller
@RequestMapping("/admin")
public class adminController {	
	@Autowired
	private tmdbService service;
	@Autowired
	private tmdbApiService apiservice;
	@Autowired
    private userService userservice;
	
	@RequestMapping("/ganre")
	@ResponseBody
	public String insertgenre() {
	    service.insertGenre();
	    return "장르 테이블 추가";
	}

	@RequestMapping("/insertMovie")
	@ResponseBody
	public String insertmovie() {
		service.insertPopularMovies();
	    return "영화, 장르_영화, 배우, 영화_배우 테이블 추가";
	}
	
	@RequestMapping("/header")
	public String header(Model model) {
	    int movieId = 10550; // 예시로 Fight Club
	    tmdbMovieDto movie = apiservice.fetchMovie(movieId); // TMDb API 호출
	    model.addAttribute("movie", movie); // 모델에 담기

	    return "fronttest"; // fronttest.html에서 사용
	}
	@GetMapping("/main")
	public String adminMain(Model model) {
		List<usersDto> users=userservice.findAll();
		model.addAttribute("users",users);
	    return "adminPage";
	}

}
