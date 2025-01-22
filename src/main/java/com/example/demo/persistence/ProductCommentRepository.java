package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.ProductComment;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Integer> {
	
	// 제품별 상품평 조회
	@Query("SELECT c FROM ProductComment c WHERE c.product.pseq=%?1%")
	List<ProductComment> findCommentByPseq(int pseq);
	
	// 제품별 상품평 수 조회
	@Query("SELECT count(c) FROM ProductComment c WHERE c.product.pseq=%?1%")
	int countCommentByPseq(int pseq);

}
