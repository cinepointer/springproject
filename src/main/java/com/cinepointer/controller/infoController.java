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

import com.cinepointer.dto.boardCommentDto;
import com.cinepointer.dto.boardDto;
import com.cinepointer.dto.movie2Dto;
import com.cinepointer.dto.reviewCommentDto;
import com.cinepointer.dto.reviewDto;
import com.cinepointer.dto.usersDto;
import com.cinepointer.service.reviewService;
import com.cinepointer.service.userService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/info")
public class infoController {

	@Autowired
	userService us;
	@Autowired
	reviewService rs;
	
	@GetMapping("/main")
	public String main(
            Model model) {
		model.addAttribute("fragment", "myInfo");
		return "myPage";
	}
	
	@GetMapping("")
	public String myInfo(
			@RequestParam(value = "fragment", required = false) String fragment,
            @ModelAttribute("message") String message,
            Model model) {
		model.addAttribute("fragment", fragment);
		return "myPage";
	}
	@GetMapping("/myInfo")
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
	    redirectAttributes.addAttribute("fragment","myInfo");
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
    public String deleteMyMovie(@RequestParam("movieNum") Long movieNum, 
    		RedirectAttributes redirectAttributes,
    		HttpSession session
    		) {
    	redirectAttributes.addAttribute("fragment","myMovie");
    	int userId = (int) session.getAttribute("userNum");
        us.deleteMovie(userId,movieNum); // 삭제 로직 실행
        return "redirect:/info"; // 다시 목록으로 리다이렉트
    }

    @GetMapping("/myReview")
    public String MyReview(HttpSession session,Model model) {

        int userId = (int) session.getAttribute("userNum");
        List<reviewDto> reviews=us.selectMyReview(userId);
        model.addAttribute("reviews",reviews);
        return "info/myReview :: myReview";  // fragment 이름 명시
    }
    
    @PostMapping("/review/delete")
    public String deleteMyReview(@RequestParam("reviewNum") int reviewNum, 
    		RedirectAttributes redirectAttributes,
    		HttpSession session
    		) {
    	redirectAttributes.addAttribute("fragment","myReview");
        rs.deleteReview(reviewNum); // 삭제 로직 실행
        return "redirect:/info"; // 다시 목록으로 리다이렉트
    }
    
    
    @GetMapping("/myBoard")
    public String Myboard(HttpSession session,Model model) {
    	int userId = (int) session.getAttribute("userNum");
        List<boardDto> boards=us.selectMyBoard(userId);

        model.addAttribute("boards",boards);
        return "info/myBoard :: myBoard";  // fragment 이름 명시
    }
    
    @GetMapping("/myComment")
    public String MyComment(HttpSession session,Model model) {
    	int userId = (int) session.getAttribute("userNum");
    	List<boardCommentDto> bcomment=us.selectMyBoardComment(userId);
    	List<reviewCommentDto> rcomment=us.selectMyReviewComment(userId);

    	
        model.addAttribute("reviewcomments",rcomment);
        model.addAttribute("boardcomments",bcomment);
        return "info/myComment :: myComment";  // fragment 이름 명시
    }
   

    
}
