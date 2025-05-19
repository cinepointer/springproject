package com.cinepointer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict; // 추가
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cinepointer.dao.userDao;
import com.cinepointer.dto.boardCommentDto;
import com.cinepointer.dto.boardDto;
import com.cinepointer.dto.movie2Dto;
import com.cinepointer.dto.reviewCommentDto;
import com.cinepointer.dto.reviewDto;
import com.cinepointer.dto.usersDto;

import jakarta.servlet.http.HttpSession;

@Service
public class userServiceImpl implements userService, UserDetailsService {

    private final userDao userDao;
    private final PasswordEncoder passwordEncoder;

    private static final String USER_SESSION_KEY = "loggedInUser";

    @Autowired
    public userServiceImpl(userDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    // Spring Security에서 로그인 시 호출되는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        usersDto user = userDao.selectUserById(username);
        System.out.println("로그인 동작");
        if (user == null) {
            throw new UsernameNotFoundException("해당 아이디의 사용자를 찾을 수 없습니다: " + username);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserId())
                .password(user.getUserPasswd()) // 암호화된 비밀번호
                .roles("USER")
                .build();
    }

    //회원가입
    @Override
    public boolean registerUser(usersDto user) {
        usersDto existingUser = userDao.selectUserById(user.getUserId());
        if (existingUser != null) {
            return false; // 아이디 중복
        }
        user.setUserPasswd(passwordEncoder.encode(user.getUserPasswd()));
        userDao.insertUser(user);
        return true;
    }
    
	    @Override
	    public String updateUserInfo(String userId, usersDto dto, String oldPassword, String newPassword, String newPasswordcheck) {
	
	        usersDto user = findById(userId);
	
	        if (oldPassword == null || oldPassword.isEmpty()) {
	            return "기존 비밀번호는 필수 입력입니다.";
	        }
	
	        if (!passwordEncoder.matches(oldPassword, user.getUserPasswd())) {
	            return "기존 비밀번호가 일치하지 않습니다.";
	        }
	
	        if (newPassword != null && !newPassword.isEmpty()) {
	            if (!newPassword.equals(newPasswordcheck)) {
	                return "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.";
	            }
	
	            // 새 비밀번호 암호화 저장
	            user.setUserPasswd(passwordEncoder.encode(newPassword));
	        }
	
	        user.setUserName(dto.getUserName());
	        user.setUserEmail(dto.getUserEmail());
	        user.setUserBirthDate(dto.getUserBirthDate());
	
	        userDao.updateUser(user);
	
	        return "success";
	    }


    @Override
    public usersDto login(String userId, String userPasswd, HttpSession session) {
        usersDto user = userDao.selectUserById(userId);
        if (user != null && passwordEncoder.matches(userPasswd, user.getUserPasswd())) {
            session.setAttribute(USER_SESSION_KEY, user);
            return user;
        }
        return null;
    }

    @Override
    public void logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }

    @Override
    public usersDto getUserInfo(int userNum) {
        return userDao.findByUserNum(userNum);
    }

    // 캐시 무효화: userCache라는 이름의 캐시에서 해당 userId에 해당하는 캐시 삭제
    @Override
    @CacheEvict(value = "userCache", key = "#user.userId")
    public void updateUser(usersDto user) {
        if (user.getUserPasswd() != null && !user.getUserPasswd().isEmpty()) {
            user.setUserPasswd(passwordEncoder.encode(user.getUserPasswd()));
        }
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(int userNum) {
        userDao.deleteUser(userNum);
    }
    
    @Override
    public void deleteUserById(String id) {
        userDao.deleteUserById(id);
    }
    
    @Override
    public usersDto findById(String userId) {
        return userDao.selectUserById(userId);
    }
    
    @Override
    public List<usersDto> findAll() {
        return userDao.selectAllUsers();
    }

    @Override
    public List<movie2Dto> getwishList(String userId) {
    	 List<movie2Dto> myMovies=userDao.selectWishListByUserId(userId);
        return myMovies;
    }
    
    @Override
    public void deleteMovie(int userId, Long movieNum) {
    	userDao.deleteMyMovie(userId,movieNum);
    	
    }
    
    @Override
    public List<boardDto> selectMyBoard(int userNum) {
    	
    	return userDao.selectMyBoard(userNum);
    }
    
    @Override
    public List<boardCommentDto> selectMyBoardComment(int userNum) {
    	
    	return userDao.selectMyBoardComment(userNum);
    }
    
    @Override
    public List<reviewDto> selectMyReview(int userNum) {
    	
    	return userDao.selectMyReview(userNum);
    }
    
    @Override
    public List<reviewCommentDto> selectMyReviewComment(int userNum) {
    	
    	return userDao.selectMyReviewComment(userNum);
    }

}
