package com.cinepointer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinepointer.dto.movie2Dto;
import com.cinepointer.dto.usersDto;
import com.cinepointer.service.userService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/info")
public class infoController {

	@Autowired
	userService us;
	
	
	@GetMapping("/main")
	public String main(
            Model model) {
		model.addAttribute("fragment", "myInformation");
		return "myPage";
		}
	
	@GetMapping("")
	public String myInfo(
            @ModelAttribute("message") String message,
            Model model) {
		
		return "myPage";
	}
	@GetMapping("/myinfo")
	public String information(Model model, HttpSession session) {
	    String userId = (String) session.getAttribute("userId");
	    usersDto user = us.findById(userId);
	    model.addAttribute("user", user);
	    

	    return "info/myInformation :: myInformation";
	}
	
	@PostMapping("/update")
	public String updateUserInfo(
	        usersDto dto,
	        @RequestParam("oldPassword") String oldPassword,
	        @RequestParam(required = false, value = "newPassword") String newPassword,
	        @RequestParam(required = false, value = "newPasswordcheck") String newPasswordcheck,
	        HttpSession session,
	        RedirectAttributes redirectAttributes) {

	    String userId = (String) session.getAttribute("userId");

	    String result = us.updateUserInfo(userId, dto, oldPassword, newPassword, newPasswordcheck);

	    if (!result.equals("success")) {
	    	redirectAttributes.addAttribute("message", result);
	        return "redirect:/info";
	    }

	    redirectAttributes.addAttribute("message", "정보가 성공적으로 수정되었습니다.");
	    return "redirect:/info";
	}

	
    @GetMapping("/myMovie")
    public String MyMovie(Model model,
    		HttpSession session) {
    	String userId = (String) session.getAttribute("userId");
    	
        List<movie2Dto> myMovies=us.getwishList(userId);

        model.addAttribute("myMovies", myMovies);
        return "info/myMovie :: myMovieList";
    }
    
    @PostMapping("/movie/delete")
    public String deleteMovieFromMyList(@RequestParam("movieNum") Long movieNum, Model model) {
        //us.deleteMovie(movieNum); // 삭제 로직 실행
        return "redirect:/info"; // 다시 목록으로 리다이렉트
    }

    @GetMapping("/myReview")
    public String MyReview(Model model) {
        // 예시: 찜한 영화 리스트
        List<String> favoriteMovies = List.of("인셉션", "인터스텔라", "다크나이트");
        model.addAttribute("favoriteMovies", favoriteMovies);
        return "info/myMovie :: myMovieList";  // fragment 이름 명시
    }
    @GetMapping("/myBoard")
    public String Myboard(Model model) {
        // 예시: 찜한 영화 리스트
        List<String> favoriteMovies = List.of("인셉션", "인터스텔라", "다크나이트");
        model.addAttribute("favoriteMovies", favoriteMovies);
        return "info/myMovieList :: myMovieList";  // fragment 이름 명시
    }
    @GetMapping("/myComment")
    public String MyComment(Model model) {
        // 예시: 찜한 영화 리스트
        List<String> favoriteMovies = List.of("인셉션", "인터스텔라", "다크나이트");
        model.addAttribute("favoriteMovies", favoriteMovies);
        return "info/myMovieList :: myMovieList";  // fragment 이름 명시
    }
   

    
}
