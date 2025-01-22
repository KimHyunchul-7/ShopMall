package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.Member;
import com.example.demo.domain.Qna;
import com.example.demo.service.QnaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class QnaController {

	@Autowired
	private QnaService qnaService;
	
	@GetMapping("/qna_list")
	public String qnaListView(HttpSession session, Model model) {
		// (1) 로그인 인증 확인
		String url = "";
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if (loginUser == null) {  // 로그인이 안되어 있음
			url = "member/login";
		} else {
			List<Qna> qnaList = qnaService.getqnaList(loginUser.getId());
			
			model.addAttribute("qnaList", qnaList);
			
			url = "qna/qnaList";
		}
		
		return url;
	}
	
	@GetMapping("/qna_view")
	public String qnaView(Qna vo, HttpSession session, Model model) {
		// (1) 로그인 인증 확인
		String url = "";
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if (loginUser == null) {  // 로그인이 안되어 있음
			url = "member/login";
		} else {
			Qna qna = qnaService.getQna(vo.getQseq());
			
			model.addAttribute("qnaVO", qna);
			url = "qna/qnaView";
		}
		
		return url;
	}
	
	@GetMapping("/qna_write_form")
	public String qnaWriteView(HttpSession session) {
		// (1) 로그인 인증 확인
		String url = "";
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if (loginUser == null) {  // 로그인이 안되어 있음
			url = "member/login";
		} else {			
			url = "qna/qnaWrite";
		}
		
		return url;
	}
	
	@PostMapping("/qna_write")
	public String qnaWriteAction(Qna qna, HttpSession session) {
		// (1) 로그인 인증 확인
		String url = "";
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if (loginUser == null) {  // 로그인이 안되어 있음
			url = "member/login";
		} else {
			// qna객체에 사용자 정보 설정
			qna.setMember(loginUser);
			
			qnaService.insertQna(qna);
			url = "redirect:qna_list";
		}
		
		return url;
	}
}










