package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Cart;
import com.example.demo.domain.Member;
import com.example.demo.domain.OrderDetail;
import com.example.demo.domain.Orders;
import com.example.demo.domain.Product;
import com.example.demo.dto.OrderVO;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/cart_insert")
	public String insertCart(@RequestParam("pseq") int pseq,
			Cart vo,
			HttpSession session) {
		String url = "";
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) { // 로그인이 안되어 있음
			url = "member/login";
		} else {
			vo.setMember(loginUser);
			
			Product p = new Product();
			p.setPseq(pseq);
			vo.setProduct(p);
			
			cartService.insertCart(vo);
			
			url = "redirect:cart_list";
		}
		
		return url;		
	}
	
	@GetMapping("/cart_list")
	public String cartList(HttpSession session, Model model) {
		// (1) 로그인 인증 확인		
		String url = "";
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) { // 로그인이 안되어 있음
			url = "member/login";
		} else {
			// (2) id를 조건으로 서비스에서 cart목록 조회:
			//     - 장바구니 조회
			//	   - 화면에 출력할 데이터 설정: cartList, totalPrice 
			List<Cart> cartList = cartService.getCartList(loginUser.getId());
			
			// 장바구니 총액 계산
			int totalAmount = 0;
			for(Cart vo : cartList) {
				totalAmount += vo.getQuantity() * vo.getProduct().getPrice2();
			}
			
			model.addAttribute("cartList", cartList);
			model.addAttribute("totalPrice", totalAmount);
			
			url = "mypage/cartList";
		}
		
		// (3) 장바구니 목록 화면 호출: cartList.html
		return url;		
	}
	
	@PostMapping("/cart_delete")
	public String deleteCart(@RequestParam("cseq") int[] cseq) {
		
		// 장바구니에서 항목 삭제
		for(int seq : cseq) {
			cartService.deleteCart(seq);
		}
		
		return "redirect:cart_list";
		
	}
	
	@PostMapping("/order_insert")
	public String orderInsert(HttpSession session, Orders order, RedirectAttributes rattr) {
		// (1) 로그인 인증 확인		
				String url = "";
				Member loginUser = (Member)session.getAttribute("loginUser");
				
				if(loginUser == null) { // 로그인이 안되어 있음
					url = "member/login";
				} else {
					// orders 객체에 사용자 정보 설정
					order.setMember(loginUser);
					
					// 주문 저장
					int oseq = orderService.insertOrder(order);
					rattr.addAttribute("oseq", oseq);
					
					url = "redirect:order_list";
				}
				
				return url;		
	}
	
	@GetMapping("order_list")
	public String orderListAction(HttpSession session,
			@RequestParam("oseq") int oseq, Model model) {
		
		// (1) 로그인 인증 확인		
		String url = "";
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) { // 로그인이 안되어 있음
			url = "member/login";
		} else {
			// 주문처리 내역 조회
			Orders order = orderService.getOrderById(loginUser.getId(), oseq);

			// 주문총액 계산
			int totalAmount = 0;
			for( OrderDetail od : order.getOrderDetail()) {
				totalAmount += od.getQuantity() * od.getProduct().getPrice2(); // getPrice1():원가, getPrice2():판매가, getPrice3():판매가-원가
			}
			
			model.addAttribute("orderList", order.getOrderDetail());
			model.addAttribute("orderDate", order.getIndate());
			model.addAttribute("totalPrice", totalAmount);
			
			url = "mypage/orderList";
		}
		
		return url;
	}
	
	// 진행중인 사용자 주문내역 조회
	@GetMapping("/mypage")
	public String myPageView(HttpSession session, Model model) {
		// (1) 로그인 인증 확인		
		String url = "";
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) { // 로그인이 안되어 있음
			url = "member/login";
		} else {
			// (1) 진행중인 주문번호 목록 조회
			List<Integer> oseqList = orderService.getSeqOrdering(loginUser.getId(), "1");
			
			// (2) 각 주문번호에 대해 주문내역 조회 및 요약정보 생성
			List<OrderVO> summaryList = new ArrayList<OrderVO>();  // 주문번호별 요약 목록 저장
			for(int oseq : oseqList) {
				OrderVO summary = new OrderVO();	
				
				// 주문번호별 주문 조회
				Orders order = orderService.getOrderById(loginUser.getId(), oseq);
				summary.setIndate(order.getIndate());
				summary.setOseq(order.getOseq());
				
				// 주문요약 정보에 상품명 저장
				int detailSize = order.getOrderDetail().size();
				
				// 첫번째 상품의 이름 저장
				String pName = order.getOrderDetail().get(0).getProduct().getName();
				if(detailSize > 1) {
					summary.setPname(pName + " 외 " + (detailSize-1) + "건");
				} else {
					summary.setPname(pName);
				}
				
				// 각 주문별 합계금액
				int amount = 0;
				for(int i=0; i<detailSize; i++) {
					amount += order.getOrderDetail().get(i).getQuantity() * 
							order.getOrderDetail().get(i).getProduct().getPrice2();					
				}
				summary.setPrice2(amount);
				
				summaryList.add(summary); // 주문용약 목록에 추가
			}
			
			// (3) 주문정보를 화면에 전달 및 화면 출력
			model.addAttribute("title", "진행중인 주문 내역");
			model.addAttribute("orderList", summaryList);	
			
			url = "mypage/mypage";
		}
		
		return url;
	}
	
	@GetMapping("/order_detail")
	public String orderDetailView(Orders vo, HttpSession session, Model model) {
		// 로그인 인증
		String url = "";
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if (loginUser == null) {  // 로그인이 안되어 있음
			url = "member/login";
		} else {
			// 로그인 완료 후
			// (1) 주문내역 조회(사용자id, 주문번호)
			Orders order = orderService.getOrderById(loginUser.getId(), vo.getOseq());
			
			// (2) 주문 총액 계산
			int amount = 0;
			for(OrderDetail detail : order.getOrderDetail()) {
				amount += detail.getQuantity() * detail.getProduct().getPrice2();
			}
			
			// (3) 화면에 데이터 전달(title, order, totalPrice) 및 화면 호출
			model.addAttribute("title", "My Page(주문 상세 정보)");
			model.addAttribute("order", order);
			model.addAttribute("totalPrice", amount);
			
			url = "mypage/orderDetail";
		}
		
		return url;
	}
	
	/*
	 * 미처리, 처리를 포함하는 모든 주문내역 조회
	 */
	@GetMapping("/order_all")
	public String orderAllView(HttpSession session, Model model) {
		// (1) 로그인 인증 확인		
				String url = "";
				Member loginUser = (Member)session.getAttribute("loginUser");
				
				if(loginUser == null) { // 로그인이 안되어 있음
					url = "member/login";
				} else {
					// (1) 진행중인 주문번호 목록 조회
					List<Integer> oseqList = orderService.getSeqOrdering(loginUser.getId(), "");
					
					// (2) 각 주문번호에 대해 주문내역 조회 및 요약정보 생성
					List<OrderVO> summaryList = new ArrayList<OrderVO>();  // 주문번호별 요약 목록 저장
					for(int oseq : oseqList) {
						OrderVO summary = new OrderVO();	
						
						// 주문번호별 주문 조회
						Orders order = orderService.getOrderById(loginUser.getId(), oseq);
						summary.setIndate(order.getIndate());
						summary.setOseq(order.getOseq());
						
						// 주문요약 정보에 상품명 저장
						int detailSize = order.getOrderDetail().size();
						
						// 첫번째 상품의 이름 저장
						String pName = order.getOrderDetail().get(0).getProduct().getName();
						if(detailSize > 1) {
							summary.setPname(pName + " 외 " + (detailSize-1) + "건");
						} else {
							summary.setPname(pName);
						}
						
						// 각 주문별 합계금액
						int amount = 0;
						for(int i=0; i<detailSize; i++) {
							amount += order.getOrderDetail().get(i).getQuantity() * 
									order.getOrderDetail().get(i).getProduct().getPrice2();					
						}
						summary.setPrice2(amount);
						
						summaryList.add(summary); // 주문용약 목록에 추가
					}
					
					// (3) 주문정보를 화면에 전달 및 화면 출력
					model.addAttribute("title", "총 주문 내역");
					model.addAttribute("orderList", summaryList);	
					
					url = "mypage/mypage";
				}
				
				return url;
	}
}
