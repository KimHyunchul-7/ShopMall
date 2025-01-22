package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	// 회원 아이디별 장바구니 목록 조회(JPQL로 구현)
	@Query("SELECT c FROM Cart c WHERE c.member.id=%?1% AND c.result='1'")
	public List<Cart> getCartList(String userId);
}
