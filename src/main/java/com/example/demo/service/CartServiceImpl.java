package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Cart;
import com.example.demo.persistence.CartRepository;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepo;
	
	@Override
	public void insertCart(Cart vo) {
		
		cartRepo.save(vo);
	}

	@Override
	public List<Cart> getCartList(String id) {
		
		return cartRepo.getCartList(id);
	}

	@Override
	public void deleteCart(int cseq) {
		
		cartRepo.deleteById(cseq);
		
	}

	@Override
	public void updateCart(int cseq) {
		// 업데이트할 cart정보 읽기
		Cart cart = cartRepo.findById(cseq).get();
		
		cart.setResult("2");  // 장바구니 처리결과를 '처리완료(2)'로 수정
		
		cartRepo.save(cart);
				
	}

}
