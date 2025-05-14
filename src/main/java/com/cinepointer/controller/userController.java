package com.cinepointer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cinepointer.dto.usersDto;
import com.cinepointer.service.cinepointerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class userController {

    private final cinepointerService cinepointerService;

    public userController(cinepointerService cinepointerService) {
        this.cinepointerService = cinepointerService;
    }
    @GetMapping("/user")
    @ResponseBody
    public String root() {
    	
    	return "main";
    }
    // 로그인 폼
    @GetMapping("/signIn")
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
    @GetMapping("/signUp")
    public String signUpForm(Model model) {
        model.addAttribute("user", new usersDto());
        return "signUp";
    }

    // 회원가입 처리
    @PostMapping("/users/signup")
    public String register(@ModelAttribute("user") usersDto user, Model model) {
    	System.out.println("회원가입 시도");
    	try {
            cinepointerService.register(user);
            return "redirect:/user";
        } catch (Exception e) {
        	System.out.print(e.getMessage());
        	
            model.addAttribute("signupError", "회원가입에 실패했습니다: " + e.getMessage());
            return "signUp";
        }
    }

    // 회원정보 조회
    @GetMapping("/users/{user_id}")
    public String getUserInfo(@PathVariable String user_id, Model model) {
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
    public String editUserForm(@PathVariable String user_id, Model model) {
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
    public String editUser(@PathVariable String user_id, @ModelAttribute("user") usersDto user, Model model) {
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
    public String deleteUser(@PathVariable String user_id, HttpSession session, Model model) {
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
        model.addAttribute("users", cinepointerService.findAllUsers()); // findAllUsers 메서드 필요
        return "userManagement";
    }

    // 로그인 에러
    @GetMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("loginError", "로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.");
        return "signIn";
    }
}
