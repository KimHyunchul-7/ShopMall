package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Admin;
import com.example.demo.domain.Member;
import com.example.demo.domain.OrderDetail;
import com.example.demo.domain.Product;
import com.example.demo.domain.Qna;
import com.example.demo.dto.SalesCountInterface;
import com.example.demo.service.AdminService;
import com.example.demo.service.MemberService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.QnaService;

@Controller
@SessionAttributes("adminUser")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private ProductService productService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private QnaService qnaService;
	
	// 파일 업로드 경로 변수 선언(application.properties 파일에서 속성값 읽기)
	@Value("${com.example.demo.upload.path}")
	private String uploadPath;
	
	@GetMapping("/admin_login_form")
	public String adminLoginView() {
		
		return "admin/main";
	}
	
	// 관리자 로그인 구현
	@PostMapping("/admin_login")
	public String adminLogin(Admin vo, Model model) {
		String url = "";
		
		// (1) 관리자 계정 인증 호출: adminCheck()
		int result = adminService.adminCheck(vo);
		
		// (2) 인증 결과에 따라 
		// -- 정상 사용자이면 상품 목록 출력(admin/productList.html) (url: admin_product_list)
		//    관리자 정보를 세션에 저장: "adminUser" 속성에 저장
		// -- 비정상 사용자이면 관리자 로그인 화면 출력 
		if (result == 1) {  // 정상 사용자
			Admin admin = adminService.getAdmin(vo.getId());
			model.addAttribute("adminUser", admin);
			
			url = "redirect:admin_product_list";
		} else {
			model.addAttribute("message", "아이디 또는 비밀번호가 맞지 않습니다.");
			url = "admin/main";
		}
		
		return url;
	}
	
	@GetMapping("/admin_logout")
	public String adminLogout(SessionStatus status) {
		
		status.setComplete();  // 세션 종료
		
		return "admin/main";
	}
	
	/*
	 * 전체 상품 목록 조회
	 */
	/*
	@GetMapping("/admin_product_list")
	public String adminProductList(
					@RequestParam(value="key", defaultValue="") String name, 
					Model model) {
		List<Product> productList = productService.getAllProducts(name);
		
		model.addAttribute("productList", productList);
		
		return "admin/product/productList";
	}
	*/
	
	/*
	 * 페이징 기능을 추가한 전체 상품 목록 조회
	 */
	@GetMapping("/admin_product_list")
	public String adminProductList(
					@RequestParam(value="key", defaultValue="") String name, 
					@RequestParam(value="page", defaultValue="1") int page,
					@RequestParam(value="size", defaultValue="10") int size,
					Model model) {
		Page<Product> productList = productService.getAllProductsByName(name, page, size);
		
		model.addAttribute("productList", productList.getContent());
		model.addAttribute("pageInfo", productList);
		System.out.println("첫 페이지?" + productList.isFirst());
		System.out.println("마지막 페이지?" + productList.isLast());
		
		return "admin/product/productList";
	}
	
	@GetMapping("/admin_product_write_form")
	public String adminProductWriteView(Model model) {
		String[] kindList = {"힐", "부츠", "샌달", "슬리퍼", "스니커즈", "세일"};
		
		model.addAttribute("kindList", kindList);
		
		return "admin/product/productWrite";
	}
	
	/* 
	 * 상품 등록 처리
	 */
	@PostMapping("/admin_product_write")
	public String adminProductWriteAction(Product vo, 
					@RequestParam(value="product_image") MultipartFile uploadFile) {
		
		if (!uploadFile.isEmpty()) { // 입력 이미지파일이 있으면
			String fileName = uploadFile.getOriginalFilename(); // 파일명 추출
			
			// 파일명이 중복되지 않도록 rename
			// UUID - 세계적으로 유니크한 ID 생성
			String uuid = UUID.randomUUID().toString();
			
			// 새로운 파일명 생성
			String saveName = uuid + "_" + fileName;
			vo.setImage(saveName);
			
			// 서버로 파일 업로드
			try {
				uploadFile.transferTo(new File(uploadPath + File.separator + saveName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		
		productService.insertProduct(vo);
		
		return "redirect:admin_product_list";
	}
	
	/*
	 * 상품 상세정보 조회 처리
	 */
	@GetMapping("/admin_product_detail")
	public String adminProductDetailAction(Product vo, Model model) {
		String[] kindList = {"", "힐", "부츠", "샌달", "슬리퍼", "스니커즈", "세일"};
		Product product = productService.getProduct(vo.getPseq());
		
		model.addAttribute("productVO", product);
		model.addAttribute("kind", kindList[Integer.parseInt(product.getKind())]);
		
		return "admin/product/productDetail";
	}
	
	@PostMapping("/admin_product_update_form")
	public String adminProductUpdateView(Product vo, Model model) {
		String[] kindList = {"힐", "부츠", "샌달", "슬리퍼", "스니커즈", "세일"};
		// 수정할 제품 조회
		Product product = productService.getProduct(vo.getPseq());
		
		model.addAttribute("productVO", product);
		model.addAttribute("kindList", kindList);
		model.addAttribute("kind", Integer.parseInt(product.getKind()));
		
		return "admin/product/productUpdate";
	}
	
	@PostMapping("/admin_product_update")
	public String adminProductUpdate(Product vo,
			@RequestParam(value="product_image") MultipartFile uploadFile,
			@RequestParam(value="nonmakeImg") String org_image) {

		if (!uploadFile.isEmpty()) { // 입력 이미지파일이 있으면
			String fileName = uploadFile.getOriginalFilename(); // 파일명 추출
			
			// 파일명이 중복되지 않도록 rename
			// UUID - 세계적으로 유니크한 ID 생성
			String uuid = UUID.randomUUID().toString();
			
			// 새로운 파일명 생성
			String saveName = uuid + "_" + fileName;
			vo.setImage(saveName);
			
			// 서버로 파일 업로드
			try {
				uploadFile.transferTo(new File(uploadPath + File.separator + saveName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		} else {  // 이미지 파일이 수정되지 않았으면
			vo.setImage(org_image);
		}
		
		if (vo.getUseyn() == null) {
			vo.setUseyn("n");
		}else {
			vo.setUseyn("y");
		}
		
		if (vo.getBestyn() == null) {
			vo.setBestyn("n");
		}else {
			vo.setBestyn("y");
		}
		
		productService.updateProduct(vo);
		System.out.println("update product" + vo);
		
		return "redirect:admin_product_list";
	}
	
	/*
	 * 회원 전체목록 조회 처리
	 */
	@RequestMapping("/admin_member_list")
	public String adminMemberList(
			@RequestParam(value="key", defaultValue="") String name,
			Model model) {
		List<Member> memberList = memberService.getMemberList(name);
		model.addAttribute("memberList", memberList);
		
		return "admin/member/memberList";
	}
	
	/*
	 * 주문 전체내역 조회 처리
	 */
	@RequestMapping("/admin_order_list")
	public String adminOrderList(
			@RequestParam(value="key", defaultValue="") String mname,
			Model model) {
		
		List<OrderDetail> orderList = orderService.getListOrderByName(mname);
		
		model.addAttribute("orderList", orderList);
		
		return "admin/order/orderList";
	}
	
	/*
	 * 주문처리 결과 수정
	 */
	@PostMapping("/admin_order_save")
	public String adminOrderSave(
			@RequestParam(value="result") int[] odseq) {
		
		for(int i=0; i<odseq.length; i++) {
			orderService.updateOrderResult(odseq[i]);
		}
		
		return "redirect:admin_order_list";
	}
	
	@RequestMapping("/admin_qna_list")  // @GetMapping과 @PostMapping을 2번 사용할것을 @RequestMapping으로 1번 사용하였음.
	public String adminQnaList(Model model) {
		// Qna 전체 목록 조회
		List<Qna> qnaList = qnaService.getAllQna();
		
		model.addAttribute("qnaList", qnaList);
		
		return "admin/qna/qnaList";
	}
	
	@PostMapping("/admin_qna_detail")
	public String adminQnaDetail(Qna vo, Model model) {
		// (1) qna 일련번호를 조건으로 Qna 조회
		Qna qna = qnaService.getQna(vo.getQseq());
		
		// (2) qnaVO 속성에 데이터 저장
		model.addAttribute("qnaVO", qna);		
		
		// (3) qnaDetail 화면 호출
		return "admin/qna/qnaDetail";
	}
	
	@PostMapping("/admin_qna_repsave")
	public String adminQnaRepsave(Qna vo) {
		
		qnaService.updateQna(vo);
		
		return "redirect:admin_qna_list";
	}
	
	/*
	 * 제품별 판매실적 화면 표시
	 */
	@RequestMapping("/admin_sales_record_form")
	public String adminProductSalesChart() {
		
		return "admin/order/salesRecords";
	}
	
	/*
	 * 제품별 판매실적 조회 처리
	 */
	@RequestMapping("sales_record_chart")
	@ResponseBody
	public List<SalesCountInterface> salesRecordChart() {
		
		return orderService.getProductSales();
	}
}















