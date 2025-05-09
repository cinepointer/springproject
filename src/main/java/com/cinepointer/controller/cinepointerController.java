package com.cinepointer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cinepointer.service.tmdbService;



@Controller
public class cinepointerController {	
	@Autowired
	private tmdbService service;
	
	//장르의 제목과 이름 업데이트용 한 번 사용후 재사용 필요 X
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

}
