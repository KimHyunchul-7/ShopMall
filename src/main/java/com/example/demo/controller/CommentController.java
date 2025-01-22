package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Member;
import com.example.demo.domain.Product;
import com.example.demo.domain.ProductComment;
import com.example.demo.service.CommentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@GetMapping("/list")
	public Map<String, Object> commentList(@RequestParam(value="pseq") int pseq) {
		Map<String, Object> commentInfo = new HashMap<>();
		
		List<ProductComment> commentList = commentService.getCommentList(pseq);
		int count = commentService.getCountCommentList(pseq);
		
		commentInfo.put("commentList", commentList);
		commentInfo.put("commentCount", count);
		
		return commentInfo;
	}
	
	@PostMapping(value="/save", produces="application/json;charset=UTF-8")
	public Map<String, Object> saveCommentAction(ProductComment vo,
			@RequestParam(value="pseq") int pseq, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if (loginUser == null) {  // 로그인이 안되어 있음
			map.put("result", "not_logedin");
		} else {
			vo.setMember(loginUser);
			
			Product p = new Product();
			p.setPseq(pseq); 	// 화면에서 받은 제품번호(pseq)를 product 객체에 저장
			vo.setProduct(p);	// ProductComment 객체에 제품정보 저장
			
			commentService.saveComment(vo); 	// 제품평을 테이블에 저장
			map.put("result", "success");
		}
		
		return map;
	}
}












