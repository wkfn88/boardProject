package com.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.dao.BoardDAO;
import com.web.vo.CommentVO;
import com.web.vo.FreeBoardVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardDAO dao;

	@RequestMapping("/freeBoard")
	public String freeBoard(Model model, HttpServletRequest request) {
		String pageNum = request.getParameter("pageNum");

		List<FreeBoardVO> list;
		int pageCount = dao.getFreeBoardPageCount();
		int pageStart = 1;

		if (pageNum == null) {
			list = dao.getFreeBoardList(0);
		} else {
			list = dao.getFreeBoardList((Integer.parseInt(pageNum) - 1) * 11);
			if (Integer.parseInt(pageNum) % 5 == 0) {
				pageStart = Integer.parseInt(pageNum) - 4;
			} else {
				pageStart = Integer.parseInt(pageNum) - (Integer.parseInt(pageNum) % 5 - 1);
			}
		}
		
		FreeBoardVO notice = dao.getNotice();
		
		model.addAttribute("notice", notice);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("pageStart", pageStart);
		model.addAttribute("requestPage", "freeBoard");
		model.addAttribute("list", list);
		return "freeBoard";
	}
	
	@RequestMapping("/notice")
	public String notice(Model model, HttpServletRequest request) {
		String pageNum = request.getParameter("pageNum");

		List<FreeBoardVO> list;
		int pageCount = dao.getFreeBoardPageCount();
		int pageStart = 1;

		if (pageNum == null) {
			list = dao.getNoticeList(0);
		} else {
			list = dao.getNoticeList((Integer.parseInt(pageNum) - 1) * 11);
			if (Integer.parseInt(pageNum) % 5 == 0) {
				pageStart = Integer.parseInt(pageNum) - 4;
			} else {
				pageStart = Integer.parseInt(pageNum) - (Integer.parseInt(pageNum) % 5 - 1);
			}
		}
		
		FreeBoardVO notice = dao.getNotice();
		
		model.addAttribute("notice", notice);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("pageStart", pageStart);
		model.addAttribute("requestPage", "freeBoard");
		model.addAttribute("list", list);
		return "notice";
	}

	@RequestMapping("/freeBoardWrite")
	public String freeBoardWrite(Model model) {

		return "freeBoardWrite";
	}
	
	@RequestMapping("/noticeWrite")
	public String noticeWrite(Model model) {
		return "noticeWrite";
	}

	@RequestMapping("/freeBoardWriteEvent")
	public String freeBoardWriteEvent(FreeBoardVO bVo, Model model, HttpSession session, HttpServletRequest request) {
		String loginUser = (String) session.getAttribute("loginUser");
		int status = Integer.parseInt(request.getParameter("status"));
		
		if (loginUser == null) {
			dao.writeFreeBoard(bVo, status);
		} else {
			bVo.setBoardPwd("0000");
			dao.writeFreeBoard(bVo, status);
		}

		return "redirect:/board/freeBoard";
	}

	@RequestMapping("/freeBoardView")
	public String freeBoardView(Model model, HttpServletRequest request) {
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));

		FreeBoardVO fbVo = dao.getFreeBoard(boardNum);
		if (fbVo == null) {
			model.addAttribute("result", "boardNumError");
			return "result";
		}

		List<CommentVO> cVo = dao.getCommentList(boardNum);
		dao.readCountUp(boardNum);

		model.addAttribute("board", fbVo);
		model.addAttribute("commentList", cVo);

		return "freeBoardView";
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String comment(CommentVO cVo, HttpServletRequest request, HttpSession session) {
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		String loginUser = (String) session.getAttribute("loginUser");
		int result = 0;

		if (loginUser == null) {
			String contentNum = boardNum + "" + dao.getCommentNext();
			result = dao.insertComment(cVo, contentNum);
		} else {
			cVo.setCommentPwd("0000");
			String commentNum = boardNum + "" + dao.getCommentNext();
			result = dao.insertComment(cVo, commentNum);
		}

		System.out.println("댓글 입력 결과 : " + result);
		return "redirect:/board/freeBoardView?boardNum=" + boardNum;
	}

	@RequestMapping("/recommend")
	public String recommend(Model model, HttpServletRequest request, HttpSession session) {
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		String loginUser = (String) session.getAttribute("loginUser");

		if (loginUser == null) {
			model.addAttribute("result", "notLogin");
			return "result";
		} else {
			if (!dao.recommendCheck(boardNum, loginUser)) {
				model.addAttribute("result", "recommend");
				return "result";
			}
		}

		dao.recommendUp(boardNum, loginUser);
		return "redirect:/board/freeBoardView?boardNum=" + boardNum;
	}

	@RequestMapping("updateBoard")
	public String updateBoard(FreeBoardVO fbVo) {
		dao.updateBoard(fbVo);

		return "redirect:/board/freeBoardView?boardNum=" + fbVo.getBoardNum();
	}

	@RequestMapping("deleteBoard")
	public String deleteBoard(HttpServletRequest request, Model model, HttpSession session) {
		String loginUser = (String) session.getAttribute("loginUser");

		if (loginUser == null) {
			model.addAttribute("result", "notLogin");
			return "result";
		} else if (!loginUser.equals(request.getParameter("memberId"))) {
			model.addAttribute("result", "notLogin");
			return "result";
		}
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		dao.deleteBoard(boardNum);
		model.addAttribute("result", "boardDelSuccess");
		model.addAttribute("boardNum", boardNum);

		return "result";
	}

	@RequestMapping("deleteComment")
	public String deleteComment(HttpServletRequest request, Model model, HttpSession session) {
		String loginUser = (String) session.getAttribute("loginUser");
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		String commentNum = request.getParameter("commentNum");

		if (loginUser == null) {
			model.addAttribute("result", "notLogin");
			return "result";
		} else if (!dao.getCommentMemberId(commentNum).equals(loginUser)) {
			model.addAttribute("result", "notLogin");
			return "result";
		}

		dao.deleteComment(commentNum);

		model.addAttribute("result", "comDelSuccess");
		model.addAttribute("boardNum", boardNum);
		return "result";

	}

	@RequestMapping("/freeBoardUpdate")
	public String freeBoardUpdate(Model model, HttpServletRequest request, HttpSession session) {
		String loginUser = (String) session.getAttribute("loginUser");
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));

		if (loginUser == null) {
			model.addAttribute("result", "notLogin");
			return "result";
		} else if (!dao.getBoardMemberId(boardNum).equals(loginUser)) {
			model.addAttribute("result", "notLogin");
			return "result";
		}

		FreeBoardVO fbVo = dao.getFreeBoard(boardNum);
		model.addAttribute("board", fbVo);
		return "freeBoardUpdate";
	}

	@RequestMapping(value = "/modal", method = RequestMethod.POST)
	public String modal(Model model, HttpServletRequest request) {
		String modalType = request.getParameter("modalType");
		String boardNum = request.getParameter("boardNum");

		if (modalType.equals("delete")) {
			String pwd = request.getParameter("pwd");
			if (dao.checkBoardPwd(Integer.parseInt(boardNum)).equals(pwd)) {
				dao.deleteBoard(Integer.parseInt(boardNum));
				model.addAttribute("result", "boardDelSuccess");
				model.addAttribute("boardNum", boardNum);
				return "result";
			} else {
				model.addAttribute("result", "delFail");
				return "result";
			}
		} else if (modalType.equals("update")) {
			String pwd = request.getParameter("pwd");
			if (dao.checkBoardPwd(Integer.parseInt(boardNum)).equals(pwd)) {
				int iboardNum = Integer.parseInt(request.getParameter("boardNum"));
				FreeBoardVO fbVo = dao.getFreeBoard(iboardNum);
				model.addAttribute("board", fbVo);
				return "freeBoardUpdate";
			} else {
				model.addAttribute("result", "delFail");
				return "result";
			}

		} else if (modalType.equals("comDelete")) {
			String commentNum = request.getParameter("commentNum");
			String pwd = request.getParameter("pwd");

			if (dao.checkComment(commentNum).equals(pwd)) {
				dao.deleteComment(commentNum);
				model.addAttribute("result", "comDelSuccess");
				model.addAttribute("boardNum", boardNum);
				return "result";
			} else {
				model.addAttribute("result", "delFail");
				return "result";
			}
		}

		return "redirect:/board/freeBoard";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchBoard(Model model, HttpServletRequest request) {
		String type = request.getParameter("type");
		String searchWord = request.getParameter("searchWord");
		String pageNum = request.getParameter("pageNum");

		List<FreeBoardVO> list;
		int pageCount = dao.getFreeBoardPageCount(type, searchWord);
		int pageStart = 1;

		if (pageNum == null) {
			list = dao.searchBoard(type, searchWord, 0);
		} else {
			list = dao.searchBoard(type, searchWord, (Integer.parseInt(pageNum) - 1) * 11);
			if (Integer.parseInt(pageNum) % 5 == 0) {
				pageStart = Integer.parseInt(pageNum) - 4;
			} else {
				pageStart = Integer.parseInt(pageNum) - (Integer.parseInt(pageNum) % 5 - 1);
			}
		}
		
		FreeBoardVO notice = dao.getNotice();
		model.addAttribute("notice", notice);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("pageStart", pageStart);
		model.addAttribute("requestPage", "search");
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("list", list);
		return "freeBoard";
	}
	
	@RequestMapping("/deleteAdmin")
	public String deleteAdmin(HttpServletRequest request) {
		String[] temp = request.getParameterValues("deleteBox");
		int[] boardNums = new int[temp.length];
		
		for( int i = 0; i < temp.length; i++ ) {
			boardNums[i] = Integer.parseInt(temp[i]);
		}
		
		for( int i = 0; i < boardNums.length; i++ ) {
			dao.deleteBoard(boardNums[i]);
		}
		
		return "redirect:/board/freeBoard";
	}

}
