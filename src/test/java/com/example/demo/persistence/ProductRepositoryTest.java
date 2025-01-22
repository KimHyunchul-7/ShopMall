package com.example.demo.persistence;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Product;

@SpringBootTest
public class ProductRepositoryTest {

	@Autowired
	ProductRepository productRepo;
	
	@Disabled
	@Test
	public void productInsert() {
		Product[] products = {
				new Product("크로커다일부츠", "2", 40000, 50000, 10000, "오리지날 크로커다일부츠 입니다.", "w2.jpg", "n", "y", new Date()),
				new Product("롱부츠", "2", 40000, 50000, 10000, "따뜻한 롱부츠 입니다.", "w-28.jpg", "n", "y", new Date()),
				new Product("힐", "1", 10000, 12000, 2000, "여성 전용 힐", "w-26.jpg", "n", "y", new Date()),
				new Product("슬리퍼", "4", 5000, 5500, 500, "편안한 슬리퍼 입니다.", "w-25.jpg", "y", "y", new Date()),
				new Product("회색힐", "1", 10000, 12000, 2000, "여성 전용 힐", "w9.jpg", "n", "y", new Date()),
				new Product("여성 부츠", "2", 12000, 18000, 6000, "여성용 부츠", "w4.jpg", "n", "y", new Date()),
				new Product("핑크샌달", "3", 5000, 5500, 500, "사계절용 샌달 입니다.", "w-10.jpg", "y", "y", new Date()),
				new Product("슬리퍼", "3", 5000, 5500, 500, "편안한 슬리퍼 입니다.", "w11.jpg", "y", "y", new Date()),
				new Product("스니커즈", "4", 15000, 20000, 5000, "활동성이 좋은 스니커즈입니다.", "w1.jpg", "n", "y", new Date()),
				new Product("샌달", "3", 5000, 5500, 500, "사계절용 샌달 입니다.", "w-09.jpg", "n", "y", new Date()),
				new Product("스니커즈", "5", 15000, 20000, 5000, "활동성이 좋은 스니커즈입니다.", "w-05.jpg", "n", "y", new Date()),
			};
		
		for (int i=0; i<products.length; i++) {
			productRepo.save(products[i]);
		}
	}
	
	@Disabled
	@Test
	public void testNewProductList() {
		List<Product> productList = productRepo.getNewProductList();
		
		System.out.println(">>> 신상품 목록");
		for(Product p : productList) {
			System.out.println(p);
		}
	}
	
	@Disabled
	@Test
	public void testBestProductList() {
		List<Product> productList2 = productRepo.getBestProductList();
		
		System.out.println(">>> 베스트상품 목록");
		for(Product p2 : productList2) {
			System.out.println(p2);
		}
	}
	
	@Disabled
	@Test
	public void testGetProduct() {
		Optional<Product> product = productRepo.findById(7);
		
		if(product.isPresent()) {
			System.out.println(product.get());  // get() : product 객체의 값을 꺼내 주는것
		} else {
			System.out.println("제품이 존재하지 않음.");
		}
	}
	
	@Disabled
	@Test
	public void testProductListByKind() {
		// 1:힐, 2:부츠, 3:샌달, 4:슬리퍼, 5:스니커즈, 6:세일상품
		List<Product> productList = productRepo.findProductByKindContaining("1");
		
		for(Product p : productList) {
			System.out.println(p);
		}
	}
	
	@Disabled
	@Test
	public void testGetAllProducts() {
		List<Product> productList = productRepo.findProductByNameContainingOrderByName("");
		
		for(Product p : productList) {
			System.out.println(p);
		}		
	}	
	
}
