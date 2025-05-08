package com.cinepointer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinepointer.dao.boardDao;

@Controller
public class BoardController {
	@Autowired
	boardDao dao;
	
	@RequestMapping("/boardList")
	public String getList(Model model) {
		model.addAttribute("boardList", dao.getList());
		return "boardListPage";
	}
	
	@RequestMapping("/boardInsert")
	public String insertBoard() {
		return "boardInsert";
	}
}
