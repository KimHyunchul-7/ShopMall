package com.example.demo.persistence;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Member;
import com.example.demo.domain.Product;
import com.example.demo.domain.ProductComment;

@SpringBootTest
public class ProductCommentRepositoryTest {
	
	@Autowired
	ProductCommentRepository commentRepo;
	@Autowired
	ProductRepository productRepo;
	@Autowired
	MemberRepository memberRepo;
	
	@Disabled
	@Test
	public void testInsertComment() {
		
		Product product = productRepo.findById(1).get();
		Member member = memberRepo.findById("one").get();
		ProductComment comment = ProductComment.builder()
				.product(product)
				.member(member)
				.content("따뜻하고 가죽질이 좋아요.")
				.build();
		
		commentRepo.save(comment);
	}
	
	@Disabled
	@Test
	public void testFindComment() {
		
		List<ProductComment> commentList = commentRepo.findCommentByPseq(1);
		int count = commentRepo.countCommentByPseq(1);
		
		System.out.println("<<< 제품명: (count=" + count + ")");
		for(ProductComment comment : commentList) {
			System.out.println(">> " + comment);
		}
		
	}
	

}
