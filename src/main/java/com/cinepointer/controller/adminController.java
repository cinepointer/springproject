package com.cinepointer.controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
	
	

	@GetMapping("/insertMovie")
	public String insertmovie() {
		if(service.existsGanre()<10) {
			service.insertGenre();
		}
		service.insertPopularMovies();
		return "redirect:/admin/main";
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
		List<usersDto> users = userservice.findAll();
		model.addAttribute("users", users);

	    return "adminPage";
	}

    // 회원정보 수정 처리
	@PostMapping("/edit/{userId}")
	public String updateUser(@PathVariable("userId") String userId, @ModelAttribute("user") usersDto userDto) {
	    userservice.updateUser(userDto); // 이 메서드가 DB 갱신 수행
	    return "redirect:/admin/main";
	}
	
	@GetMapping("/editForm/{userId}")
	public String getEditForm(@PathVariable("userId") String userId, Model model) {
		    usersDto user = userservice.findById(userId);
		    model.addAttribute("user", user);
		return "fragment/editform :: editFormFragment";
	}
	
	@PostMapping("/delete/{userId}")
	public String deleteUser(@PathVariable("userId") String userId, Model model) {
	    userservice.deleteUserById(userId);
	    return "redirect:/admin/main";
	}
	@GetMapping("/searchUser")
	public String getUser(@RequestParam("keyword") String keyword, Model model) {
		List<usersDto> users = userservice.searchUsersByKeyword(keyword);
		
		model.addAttribute("users", users);
	    return "adminPage";
	}

}
