package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Cart;

public interface CartService {
	
	void insertCart(Cart vo);
	
	List<Cart> getCartList(String id);
	
	public void deleteCart(int cseq);
	
	void updateCart(int cseq);

}
