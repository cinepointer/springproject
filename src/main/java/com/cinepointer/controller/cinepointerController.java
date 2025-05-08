package com.cinepointer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinepointer.dao.tmdbDao;
import com.cinepointer.dto.tmdbGenreDto;
import com.cinepointer.service.tmdbService;



@Controller
public class cinepointerController {
	@Autowired
	tmdbDao dao;
	
	@Autowired
	private tmdbService service;
	
	@RequestMapping("/tmdbinsert")
	public void insertmovie() {
		List<tmdbGenreDto> genre=service.insertGenre();
		
		
		
	}
}
