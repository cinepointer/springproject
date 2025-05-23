package com.cinepointer.controller;

import java.security.Principal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String signInForm(Model model, HttpSession session) {
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        // 여기서는 FlashAttribute "msg"를 자동으로 모델에서 받을 수 있음
        // 만약 템플릿에서 th:text="${msg}"로 접근하고 싶다면 아래 코드 추가
        if (model.containsAttribute("msg")) {
            model.addAttribute("message", model.getAttribute("msg"));
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
    public String register(@ModelAttribute("user") usersDto user, RedirectAttributes redirectAttributes) {
        user.setRoleName("ROLE_USER");
        if(userService.findAll() == null) {
            user.setRoleName("ROLE_ADMIN");
        }
        try {
            if(userService.registerUser(user)) {
                redirectAttributes.addFlashAttribute("errorMessage", "이미 있는 아이디입니다.");
                return "redirect:/signup";
            }
            redirectAttributes.addFlashAttribute("msg", "회원가입에 성공하였습니다.");
            return "redirect:/signIn";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "회원가입에 실패했습니다: " + e.getMessage());
            return "redirect:/signup";
        }
    }


    @GetMapping("/login")
    public String login(HttpServletRequest request, Authentication auth) {
        String loginId = auth.getName();
        request.getSession().setAttribute("userId", loginId);
        usersDto dto=userService.findById(loginId);
        request.getSession().setAttribute("userNum", dto.getUserNum());
        Set<String> roleNames = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toSet());
        
        if (roleNames.contains("ROLE_ADMIN")) {
        	request.getSession().setAttribute("userRole", "ADMIN");
            return "redirect:/admin/main";
        }
        return "redirect:/movies";
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
    @GetMapping("/myPage")
    public String mp() {
        
        return "myPage";
    }
    
    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request) {
        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");

        if (email == null || name == null) {
            throw new IllegalArgumentException("Google 사용자 정보 부족");
        }



        if (userService.findById(email) == null) {
        	usersDto user = new usersDto();
	        user.setUserId(email);
	        user.setUserPasswd(UUID.randomUUID().toString());
	        user.setUserEmail(email);
	        user.setUserName(name);
	        user.setRoleName("ROLE_USER");
        	
        	
            userService.registerUser(user);
        }

        request.getSession().setAttribute("userId", email);
        usersDto dto=userService.findById(email);
        request.getSession().setAttribute("userNum", dto.getUserNum());
        return "redirect:/";
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



