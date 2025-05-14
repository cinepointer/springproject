package com.cinepointer.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinepointer.dto.movieDto;

@Controller
@RequestMapping("/info")
public class infoController {
	@GetMapping("/")
	public String myInfo(Model model) {
        //List<usersDto> users = userService.getAllUsers();
        //model.addAttribute("userList", users);
        return "myMoviePage";
    }
    @GetMapping("/myMovieList")
    public String MyMovie(Model model) {
        List<movieDto> myMovies=null;
        model.addAttribute("favoriteMovies", myMovies);
        return "info/myMovieList :: myMovieList";  // fragment 이름 명시
    }
    
    @GetMapping("/myReviewList")
    public String MyReview(Model model) {
        // 예시: 찜한 영화 리스트
        List<String> favoriteMovies = List.of("인셉션", "인터스텔라", "다크나이트");
        model.addAttribute("favoriteMovies", favoriteMovies);
        return "info/myMovieList :: myMovieList";  // fragment 이름 명시
    }
    @GetMapping("/myBoardList")
    public String Myboard(Model model) {
        // 예시: 찜한 영화 리스트
        List<String> favoriteMovies = List.of("인셉션", "인터스텔라", "다크나이트");
        model.addAttribute("favoriteMovies", favoriteMovies);
        return "info/myMovieList :: myMovieList";  // fragment 이름 명시
    }
    @GetMapping("/myCommentList")
    public String MyComment(Model model) {
        // 예시: 찜한 영화 리스트
        List<String> favoriteMovies = List.of("인셉션", "인터스텔라", "다크나이트");
        model.addAttribute("favoriteMovies", favoriteMovies);
        return "info/myMovieList :: myMovieList";  // fragment 이름 명시
    }
   

    
}
