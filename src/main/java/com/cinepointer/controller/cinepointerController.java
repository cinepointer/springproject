package com.cinepointer.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cinepointer.dto.tmdbMovieDto;
import com.cinepointer.service.tmdbApiService;
import com.cinepointer.service.tmdbService;



@Controller
public class cinepointerController {	
	@Autowired
	private tmdbService service;
	@Autowired
	private tmdbApiService apiService;
	@Autowired
    //private UserService userService;
	
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
	    tmdbMovieDto movie = apiService.fetchMovie(movieId); // TMDb API 호출
	    model.addAttribute("movie", movie); // 모델에 담기

	    return "fronttest"; // fronttest.html에서 사용
	}
	@GetMapping("/mymovie")
	public String myInfo(Model model) {
        //List<usersDto> users = userService.getAllUsers();
        //model.addAttribute("userList", users);
        return "myMoviePage";
    }
	
	
    @GetMapping("/users")
    public String listUsers(Model model) {
        //List<usersDto> users = userService.getAllUsers();
        //model.addAttribute("userList", users);
        return "adminPage";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        //userService.deleteUser(id);
        return "redirect:/users";
    }

    @PostMapping("/user/role/{id}")
    public String changeUserRole(@PathVariable Long id) {
        //userService.toggleUserRole(id);
        return "redirect:/users";
    }

}
