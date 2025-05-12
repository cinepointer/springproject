package com.cinepointer.controller;

import com.cinepointer.dto.usersDto;
import com.cinepointer.service.userService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class userController { // 앞글자 소문자
    private final userService service;

    public userController(userService service) { // 앞글자 소문자
        this.service = service;
    }

    @GetMapping("/signup")
    public String signUpPage() { return "signup"; }

    @PostMapping("/signup")
    public String signUp(usersDto user) {
        service.registerUser(user);
        return "redirect:/users/signin";
    }

    @GetMapping("/signin")
    public String signInPage() { return "signin"; }

    @PostMapping("/signin")
    public String signIn(@RequestParam String userId, @RequestParam String userPasswd, Model model) {
        usersDto user = service.login(userId, userPasswd, null);
        if (user != null) {
            model.addAttribute("user", user);
            return "redirect:/mainpage";
        } else {
            model.addAttribute("error", "로그인 실패");
            return "signin";
        }
    }

    @GetMapping("/mypage")
    public String myPage(@RequestParam int userNum, Model model) {
        model.addAttribute("user", service.getUserInfo(userNum));
        return "mypage";
    }

    @PostMapping("/update")
    public String updateUser(usersDto user) {
        service.updateUser(user);
        return "redirect:/users/mypage?userNum=" + user.getUserNum();
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam int userNum) {
        service.deleteUser(userNum);
        return "redirect:/";
    }
}
