package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.OrderDetail;
import com.example.demo.domain.Orders;
import com.example.demo.dto.SalesCountInterface;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	
	// 다음 주문번호 생성
	@Query(value="SELECT NVL2(MAX(oseq), MAX(oseq)+1, 1) FROM orders", nativeQuery=true) // nativeQuery: 순수한 데이터베이스 SQL문법을 따르는 쿼리임
	public int selectMaxOseq();
	
	// 사용자별 주문상세내역 조회(JPQL 사용)
	@Query("SELECT od FROM OrderDetail od " +
		   " INNER JOIN Orders o ON od.order.oseq=o.oseq " + 
	       " INNER JOIN Product p ON od.product.pseq=p.pseq " +
		   " INNER JOIN Member m ON od.order.member.id=m.id " +
	       " WHERE o.member.id=%?1% AND od.order.oseq=%?2% AND od.result LIKE %?3%")
	
	public List<OrderDetail> getListOrderById(String id, int oseq, String result);
	
	// 사용자별 주문내역 조회(JPQL 사용)
	@Query("SELECT o FROM Orders o " +
		   "INNER JOIN Member m ON o.member.id=m.id " +
		   "WHERE o.member.id=%?1% AND o.oseq=%?2%") 
	public Orders getOrderByMemberId(String id, int oseq);
	
	// 사용자별 미처리 주문번호 조회(JPQL 사용)
	@Query("SELECT DISTINCT od.order.oseq FROM OrderDetail od " +
		   "WHERE od.order.member.id=%?1% AND od.result LIKE %?2% ORDER BY od.order.oseq ASC")
	public List<Integer> getSeqOrdering(String id, String result);
	
	// 사용자 이름을 조건으로 주문내역 조회
	@Query("SELECT od FROM OrderDetail od " +
			" WHERE od.order.member.name LIKE %?1% " +
			" ORDER BY od.result, od.order.oseq DESC")
	public List<OrderDetail> getOrderListByName(String mname);
	
	// 제품별 판매 실적 조회
	@Query(value="SELECT pname, SUM(quantity) sales_count"
		   + " FROM order_view "
		   + " WHERE result='2' "
		   + " GROUP BY pname", nativeQuery=true)
	List<SalesCountInterface> findSalesCountReport();

}
