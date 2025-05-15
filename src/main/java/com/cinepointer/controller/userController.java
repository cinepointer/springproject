package com.cinepointer.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinepointer.dto.movieDto;
import com.cinepointer.dto.usersDto;
import com.cinepointer.service.userService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class userController {

    private final userService userService;
    private final PasswordEncoder passwordEncoder;

    public userController(userService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

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

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        usersDto user = new usersDto();
        model.addAttribute("user", user);
        return "signUp";
    }

    @PostMapping("/users/signup")
    public String register(@ModelAttribute("user") usersDto user, Model model) {
        user.setRoleName("ROLE_USER");
        try {
            userService.registerUser(user);
            return "redirect:/signIn";
        } catch (Exception e) {
            System.out.print(e.getMessage());
            model.addAttribute("signupError", "회원가입에 실패했습니다: " + e.getMessage());
            return "redirect:/signin";
        }
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Authentication auth) {
        String loginId = auth.getName();
        request.getSession().setAttribute("userId", loginId);

        Set<String> roleNames = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toSet());
        System.out.println(roleNames);
        if (roleNames.contains("ROLE_ADMIN")) {
            return "redirect:/admin/main";
        }
        return "mainPage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("userId");
            session.invalidate();
        }
        return "redirect:/signin";
    }

    // 회원정보 조회 (세션 기반 마이페이지)
    @GetMapping("/myPage")
    public String myPage(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        usersDto user = userService.findById(userId);
        if (user == null) {
            return "redirect:/login";
        }
        List<movieDto> wishlist;
        try {
            wishlist = userService.getwishList(userId);
        } catch (Exception e) {
            wishlist = new ArrayList<>();
        }
        model.addAttribute("user", user);
        model.addAttribute("wishlist", wishlist);
        return "myPage";
    }

    // 회원정보 조회 (URL 접근, 방어코드 추가)
    @GetMapping("/users/{user_id}")
    public String getUserInfo(@PathVariable("user_id") String user_id, Model model) {
        usersDto user = userService.findById(user_id);
        List<movieDto> wishlist = new ArrayList<>();
        if (user != null) {
            try {
                wishlist = userService.getwishList(user_id);
            } catch (Exception e) {
                // 무시
            }
            model.addAttribute("user", user);
            model.addAttribute("wishlist", wishlist);
        } else {
            model.addAttribute("user", null);
            model.addAttribute("wishlist", wishlist);
            model.addAttribute("error", "회원을 찾을 수 없습니다.");
        }
        return "myPage";
    }

    @GetMapping("/users/{user_id}/edit")
    public String editUserForm(@PathVariable("user_id") String user_id, Model model) {
        usersDto user = userService.findById(user_id);
        if (user == null) {
            model.addAttribute("error", "회원을 찾을 수 없습니다.");
            return "userEdit";
        }
        model.addAttribute("user", user);
        return "userEdit";
    }

    @PostMapping("/users/{user_id}/edit")
    public String editUser(@PathVariable("user_id") String user_id, @ModelAttribute("user") usersDto user, Model model) {
        try {
            userService.updateUser(user);
            return "redirect:/users/" + user_id + "?msg=회원정보가+수정되었습니다.";
        } catch (Exception e) {
            model.addAttribute("editError", "회원정보 수정에 실패했습니다: " + e.getMessage());
            return "userEdit";
        }
    }

    @PostMapping("/users/{user_id}/delete")
    public String deleteUser(@PathVariable("user_id") int user_id, HttpSession session, Model model) {
        try {
            userService.deleteUser(user_id);
            session.invalidate();
            return "redirect:/signIn?msg=회원탈퇴가+완료되었습니다.";
        } catch (Exception e) {
            model.addAttribute("deleteError", "회원탈퇴에 실패했습니다: " + e.getMessage());
            return "myPage";
        }
    }

    @GetMapping("/admin/users")
    public String manageUsers(Model model) {
        // model.addAttribute("users", userService.findAllUsers());
        return "userManagement";
    }

    @GetMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("loginError", "로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.");
        return "signIn";
    }

    // 비밀번호 변경
    @PostMapping("/users/password-edit")
    public String editPassword(
        @RequestParam String currentPassword,
        @RequestParam String newPassword,
        @RequestParam String confirmPassword,
        Principal principal,
        Model model
    ) {
        String userId = principal.getName();
        usersDto user = userService.findById(userId);
        if (user == null) {
            model.addAttribute("error", "사용자 정보를 찾을 수 없습니다.");
            return "myPage";
        }

        if (!passwordEncoder.matches(currentPassword, user.getUserPasswd())) {
            model.addAttribute("error", "현재 비밀번호가 일치하지 않습니다.");
            return "myPage";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "새 비밀번호가 일치하지 않습니다.");
            return "myPage";
        }

        user.setUserPasswd(passwordEncoder.encode(newPassword));
        userService.updateUser(user);

        model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
        return "redirect:/myPage";
    }

}
