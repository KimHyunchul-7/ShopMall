package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.OrderDetail;
import com.example.demo.domain.Orders;
import com.example.demo.dto.SalesCountInterface;

public interface OrderService {
	
	// 주문번호 생성
	int getMaxOseq();
	
	// 주문 저장
	int insertOrder(Orders vo);
	
	// 사용자별 상세주문 내역
	List<OrderDetail> getListOrderDetailById(String id, int oseq);
	
	// 주문상세 정보 저장
	void insertOrderDetail(OrderDetail vo);
	
	// 사용자별 주문처리 내역 조회
	Orders getOrderById(String id, int oseq);
	
	// 사용자별 미처리 주문번호 조회
	List<Integer> getSeqOrdering(String id, String result);
	
	// 이름을 조건으로 주문내역 조회
	List<OrderDetail> getListOrderByName(String mname);
	
	// 주문 결과처리
	void updateOrderResult(int odseq);
	
	// 제품별 판매실적 조회
	List<SalesCountInterface> getProductSales();
}
