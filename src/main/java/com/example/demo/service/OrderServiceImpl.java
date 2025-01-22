package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Cart;
import com.example.demo.domain.OrderDetail;
import com.example.demo.domain.Orders;
import com.example.demo.domain.Product;
import com.example.demo.dto.SalesCountInterface;
import com.example.demo.persistence.OrderDetailRepository;
import com.example.demo.persistence.OrdersRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrdersRepository orderRepo;
	
	@Autowired
	private OrderDetailRepository orderDetailRepo;
	
	@Autowired
	private CartService cartService;
	
	@Override
	public int getMaxOseq() {
		
		return orderRepo.selectMaxOseq();
	}

	@Override
	public int insertOrder(Orders vo) {
		// (1) 신규 주문번호 할당받음
		int oseq = getMaxOseq();
		vo.setOseq(oseq);
		
		// (2) 신규주문을 Orders 테이블에 저장
		orderRepo.save(vo);
		
		// (3) 장바구니 목록을 읽어 orderDetail 테이블에 저장
		List<Cart> cartList = cartService.getCartList(vo.getMember().getId());
		
		for(Cart cart : cartList) {
			OrderDetail orderDetail = new OrderDetail();
			
			// 주문정보 저장
			orderDetail.setOrder(vo);
			
			// 상품정보 저장
			Product p = cart.getProduct();
			orderDetail.setProduct(p);
			orderDetail.setQuantity(cart.getQuantity());
			
			// 주문상세 정보 저장
			insertOrderDetail(orderDetail);
			
			// 장바구니 처리결과 업데이터: '1' -> '2'
			cartService.updateCart(cart.getCseq());
		}
		
		return oseq;
	}
	
	/*
	 * 주문상세 내역 조회
	 */
	@Override
	public List<OrderDetail> getListOrderDetailById(String id, int oseq) {
		
		return orderRepo.getListOrderById(id, oseq, "1");
	}

	@Override
	public void insertOrderDetail(OrderDetail vo) {
		
		orderDetailRepo.save(vo);
		
	}
	
	/*
	 * 장바구니에서 주문처리 수행 후, Orders 테이블의 처리내역 조회
	 */
	@Override
	public Orders getOrderById(String id, int oseq) {
		
		return orderRepo.getOrderByMemberId(id, oseq);
	}

	@Override
	public List<Integer> getSeqOrdering(String id, String result) {
		
		return orderRepo.getSeqOrdering(id, result);
	}

	@Override
	public List<OrderDetail> getListOrderByName(String name) {
		
		return orderRepo.getOrderListByName(name);
	}

	@Override
	public void updateOrderResult(int odseq) {
		
		// 주문상세 조회
		OrderDetail od = orderDetailRepo.findById(odseq).get();
		
		// 처리결과 수정
		od.setResult("2");
		
		orderDetailRepo.save(od);		
	}

	@Override
	public List<SalesCountInterface> getProductSales() {
		
		return orderRepo.findSalesCountReport();
	}

}
