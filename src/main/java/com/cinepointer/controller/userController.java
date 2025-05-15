package com.cinepointer.controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinepointer.dto.usersDto;
import com.cinepointer.service.userService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class userController {

    private final userService cinepointerService;

    public userController(userService cinepointerService) {
        this.cinepointerService = cinepointerService;
    }
    // 로그인 폼
    @GetMapping("/signin")
    public String signInForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "msg", required = false) String msg,
            Model model) {
        if (error != null) {
            model.addAttribute("loginError", "로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.");
        }
        if (msg != null) {
            model.addAttribute("msg", msg);
        }
        return "signIn";
    }

    // 회원가입 폼
    @GetMapping("/signup")
    public String signUpForm(Model model) {
    	usersDto user = new usersDto();
        user.setUserBirthDate("1999-01-01"); // 기본값 설정
        model.addAttribute("user", user);
        return "signUp";
    }

    // 회원가입 처리
    @PostMapping("/users/signup")
    public String register(@ModelAttribute("user") usersDto user, Model model) {
    	user.setRoleName("ROLE_USER");
    	try {
            cinepointerService.registerUser(user);
            return "redirect:/signIn";
        } catch (Exception e) {
        	System.out.print(e.getMessage());
            model.addAttribute("signupError", "회원가입에 실패했습니다: " + e.getMessage());
            return "signUp";
        }
    }
    
    @GetMapping("/login")
    public String login(HttpServletRequest request, Authentication authentication) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String loginId = auth.getName();
    	request.getSession().setAttribute("userId", loginId);
    	Collection<? extends GrantedAuthority> role = auth.getAuthorities();
    	System.out.println(role);
        return "fronttest";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // 세션 객체를 가져옵니다.
        HttpSession session = request.getSession(false); // 세션이 존재하면 가져오고, 없으면 null 반환

        // 세션이 존재하면 userId를 제거하고 세션을 종료합니다.
        if (session != null) {
            session.removeAttribute("userId");  // "userId" 속성 제거
            session.invalidate();  // 세션 종료
        }

        // 로그아웃 후 리다이렉트 또는 다른 페이지로 이동
        return "redirect:/signin";  // 예: 로그인 페이지로 리다이렉트
    }
    
    // 회원정보 조회
    @GetMapping("/users/{user_id}")
    public String getUserInfo(@PathVariable("user_id") String user_id, Model model) {
        usersDto user = cinepointerService.findById(user_id);
        if (user == null) {
            model.addAttribute("error", "회원을 찾을 수 없습니다.");
            return "myPage";
        }
        model.addAttribute("user", user);
        return "myPage";
    }

    // 회원정보 수정 폼
    @GetMapping("/users/{user_id}/edit")
    public String editUserForm(@PathVariable("user_id") String user_id, Model model) {
        usersDto user = cinepointerService.findById(user_id);
        if (user == null) {
            model.addAttribute("error", "회원을 찾을 수 없습니다.");
            return "userEdit";
        }
        model.addAttribute("user", user);
        return "userEdit";
    }

    // 회원정보 수정 처리
    @PostMapping("/users/{user_id}/edit")
    public String editUser(@PathVariable("user_id") String user_id, @ModelAttribute("user") usersDto user, Model model) {
        try {
            // user_id 보존
            //user.setUser_id(user_id);
            cinepointerService.updateUser(user); // updateUser 메서드가 서비스에 있어야 함
            return "redirect:/users/" + user_id + "?msg=회원정보가+수정되었습니다.";
        } catch (Exception e) {
            model.addAttribute("editError", "회원정보 수정에 실패했습니다: " + e.getMessage());
            return "userEdit";
        }
    }

    // 회원탈퇴
    @PostMapping("/users/{user_id}/delete")
    public String deleteUser(@PathVariable("user_id") int user_id, HttpSession session, Model model) {
        try {
            cinepointerService.deleteUser(user_id); // deleteUser 메서드가 서비스에 있어야 함
            session.invalidate(); // 세션 종료(로그아웃)
            return "redirect:/signIn?msg=회원탈퇴가+완료되었습니다.";
        } catch (Exception e) {
            model.addAttribute("deleteError", "회원탈퇴에 실패했습니다: " + e.getMessage());
            return "myPage";
        }
    }

    // 사용자 권한 관리 (관리자만 접근 예시)
    @GetMapping("/admin/users")
    public String manageUsers(Model model) {
        //model.addAttribute("users", cinepointerService.findAllUsers()); // findAllUsers 메서드 필요
        return "userManagement";
    }

    // 로그인 에러
    @GetMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("loginError", "로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.");
        return "signIn";
    }
    
    @GetMapping("/mypage")
    public String myPage(Model model, HttpSession session) {
        usersDto user = (usersDto) session.getAttribute("loginUser");
        if (user == null) {
            // 로그인 안했으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
        // 사용자 정보를 모델에 담기
        model.addAttribute("user", user);

        // myPage.html 템플릿 반환
        return "myPage";
    }
}
