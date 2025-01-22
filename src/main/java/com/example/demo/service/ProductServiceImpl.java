package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Product;
import com.example.demo.persistence.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	@Override
	public List<Product> getNewProductList() {
		
		return productRepo.getNewProductList();

	}

	@Override
	public List<Product> getBestProductList() {
		
		return productRepo.getBestProductList();

	}

	@Override
	public Product getProduct(int pseq) {

		return productRepo.findById(pseq).get();  // get() : product 객체의 값을 꺼내 주는것
	}

	@Override
	public List<Product> getProductListByKind(String kind) {
		
		return productRepo.findProductByKindContaining(kind);
	}

	@Override
	public List<Product> getAllProducts(String name) {
		
		return productRepo.findProductByNameContainingOrderByName(name);
	}

	@Override
	public void insertProduct(Product vo) {
		
		productRepo.save(vo);		
	}

	@Override
	public void updateProduct(Product vo) {
		Product p = productRepo.findById(vo.getPseq()).get();
		
		vo.setRegdate(p.getRegdate());  // 기존의 등록일 사용
		productRepo.save(vo);
		
	}

	@Override
	public Page<Product> getAllProductsByName(String name, int page, int size) {
		// page 번호는 0부터 시작함. 제품명(name)순으로 정렬
		Pageable paging = PageRequest.of(page-1, size, Direction.ASC, "name");
		
		
		return productRepo.findAllProductByNameContaining(name, paging);
	}

}
