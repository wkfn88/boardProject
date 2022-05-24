package com.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.dao.BoardDAO;
import com.web.vo.FreeBoardVO;

@Controller
public class HomeController {
	
	@Autowired
	BoardDAO dao;

	@RequestMapping("/")
	public String home(Model model) {
		
		List<FreeBoardVO> fbVo = dao.getLatelyFreeBoardList();
		List<FreeBoardVO> fbVoReco = dao.getRecommendFreeBoardList();
		
		model.addAttribute("boardList", fbVo);
		model.addAttribute("recommendList", fbVoReco);

		return "main";
	}

	@RequestMapping("/test")
	public String test() {

		return "test";
	}
}
