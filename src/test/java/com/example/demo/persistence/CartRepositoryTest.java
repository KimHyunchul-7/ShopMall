package com.example.demo.persistence;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Cart;
import com.example.demo.domain.Member;
import com.example.demo.domain.Product;

@SpringBootTest
public class CartRepositoryTest {

	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private ProductRepository productRepo;
	
	@Disabled
	@Test
	public void testInsertCart() {
		Member member = memberRepo.findById("one").get();
		Product product = productRepo.findById(2).get();
		
		Cart cart = Cart.builder()
				.member(member)
				.product(product)
				.quantity(1)
				.build();
		
		cartRepo.save(cart);
		
		Cart cart2 = Cart.builder()
				.member(member)
				.product(product)
				.quantity(1)
				.build();
		
		cartRepo.save(cart2);
	}
	
	@Disabled
	@Test
	public void testGetCart() {
		Optional<Cart> item = cartRepo.findById(1);
		
		if(item.isPresent()) {
			Cart cart = item.get();
			System.out.println(cart);
			System.out.println("사용자명: " + cart.getMember().getName());
			System.out.println("제품명: " + cart.getProduct().getName());
		} else {
			System.out.println("제품이 존재하지 않습니다.");
		}
	}
	
	@Disabled
	@Test
	public void testGetCartList() {
		List<Cart> cartList = cartRepo.getCartList("one");
		
		for(Cart cart : cartList) {
			System.out.println(cart);
		}
	}
}




