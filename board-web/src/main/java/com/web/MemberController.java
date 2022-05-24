package com.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.dao.MemberDAO;
import com.web.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberDAO dao;

	@RequestMapping("/registPage")
	public String registPage() {
		return "regist";
	}

	@RequestMapping(value = "/registEvent", method = RequestMethod.POST)
	public String memberIdCheck(MemberVO mVo, Model model, HttpServletRequest request) {
		String action = request.getParameter("action");

		if (action.equals("아이디 중복 확인")) {
			String memberId = request.getParameter("memberId");
			if (dao.isMemberId(memberId)) {
				model.addAttribute("message", "사용가능한 아이디 입니다.");
				model.addAttribute("mVo", mVo);
				return "regist";
			} else {
				model.addAttribute("message", "중복된 아이디가 있습니다.");
				model.addAttribute("mVo", mVo);
				return "regist";
			}

		} else if (action.equals("회원가입")) {
			int result = dao.createMember(mVo);
			if (result == 1) {
				model.addAttribute("result", "RegistSuccess");
			} else if (result == -1) {
				model.addAttribute("result", "RegistFail");
			}
		}
		return "result";
	}

	@RequestMapping(value="/loginEvent", method = RequestMethod.POST)
	public String memberLogin(Model model, HttpServletRequest request, HttpSession session) {
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");

		String pwd = dao.getMemberPwd(userId);

		if (pwd == null) {
			model.addAttribute("result", "memberCheckFail");
			return "result";
		} else {
			if (pwd.equals(userPwd)) {
				MemberVO mVo = dao.getMember(userId);
				
				if( mVo.getStatus() == 1 ) {
					session.setAttribute("loginType", "admin");
				}
				
				session.setAttribute("loginUser", userId);
				model.addAttribute("result", "memberCheckSuccess");
				return "redirect:/";
			} else {
				model.addAttribute("result", "memberCheckFail");
				return "result";
			}
		}
	}
	
	@RequestMapping("/logout")
	public String memberLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
