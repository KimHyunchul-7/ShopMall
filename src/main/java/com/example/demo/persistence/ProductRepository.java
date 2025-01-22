package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	// 신상품 조회
	@Query(value="SELECT * FROM new_pro_view", nativeQuery=true)
	List<Product> getNewProductList();
	
	// 베스트 상품 조회
	@Query(value="SELECT * FROM best_pro_view", nativeQuery=true)
	List<Product> getBestProductList();
	
	// 상품 종류별 조회
	List<Product> findProductByKindContaining(String kind);
	
	// 전체상품 조회(상품명으로 검색 포함)
	List<Product> findProductByNameContainingOrderByName(String name);
	
	// 전체상품 조회(페이징 처리 포함)
	Page<Product> findAllProductByNameContaining(String name, Pageable pageable);

}
