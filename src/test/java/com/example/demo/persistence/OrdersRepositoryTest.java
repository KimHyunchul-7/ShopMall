package com.example.demo.persistence;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Member;
import com.example.demo.domain.OrderDetail;
import com.example.demo.domain.Orders;
import com.example.demo.domain.Product;

@SpringBootTest
public class OrdersRepositoryTest {
	
	@Autowired
	private OrdersRepository ordersRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private OrderDetailRepository orderDetailRepo;
	
	@Disabled
	@Test
	public void testInsertOrders() {
		// 사용자 조회
		Member member1 = memberRepo.findById("one").get();
		
		// 주문번호 할당
		int oseq = ordersRepo.selectMaxOseq();
		
		// 주문 데이터 생성
		Orders order1 = Orders.builder()
				.oseq(oseq)
				.member(member1)
				.indate(new Date())
				.build();
		
		// 첫번째 주문 데이터 insert
		ordersRepo.save(order1);
		
		// 두번째 주문 데이터 생성 및 insert
		oseq = ordersRepo.selectMaxOseq();
		Orders order2 = Orders.builder()
				.oseq(oseq)
				.member(member1)
				.indate(new Date())
				.build();
		ordersRepo.save(order2);
		
		// 세번째 주문 데이터 생성 및 insert
		Member member2 = memberRepo.findById("two").get();
		oseq = ordersRepo.selectMaxOseq();
		Orders order3 = Orders.builder()
				.oseq(oseq)
				.member(member2)
				.indate(new Date())
				.build();
		ordersRepo.save(order3);
	}
	
	@Disabled
	@Test
	public void testInsertOrderDetail() {
		Orders order1 = ordersRepo.findById(1).get();
		Orders order2 = ordersRepo.findById(2).get();
		Orders order3 = ordersRepo.findById(3).get();
		
		Product product1 = productRepo.findById(1).get();
		Product product2 = productRepo.findById(2).get();
		Product product3 = productRepo.findById(3).get();
		Product product4 = productRepo.findById(4).get();
		Product product6 = productRepo.findById(6).get();
		
		OrderDetail[] odArr = {
				new OrderDetail(0, order1, product1, 1, "1"),	
				new OrderDetail(0, order1, product2, 2, "1"),
				new OrderDetail(0, order2, product4, 3, "1"),
				new OrderDetail(0, order3, product3, 1, "1"),
				new OrderDetail(0, order3, product2, 1, "1"),
				new OrderDetail(0, order3, product6, 2, "1"),
				new OrderDetail(0, order3, product1, 2, "1")
			};
			
			for(int i=0; i<odArr.length; i++) {
				orderDetailRepo.save(odArr[i]);
			}
	}
	
	@Disabled
	@Test
	public void testGetListOrderById() {
		System.out.println("<<< 주문 상세 목록 >>>");
		
		List<OrderDetail> orderList = ordersRepo.getListOrderById("two", 3, "1");
		
		for(OrderDetail od : orderList) {
			System.out.println(od);
		}
	}
	
	@Disabled
	@Test
	public void testGetSeqOrdering() {
		List<Integer> oseqList = ordersRepo.getSeqOrdering("one", "1");
		
		System.out.println("<<<사용자별 미처리 주문번호>>>");
		for(int oseq: oseqList) {
			System.out.println(oseq + " ");
		}
	}

}
