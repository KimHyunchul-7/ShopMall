package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.domain.Product;

public interface ProductService {
	
	public List<Product> getNewProductList();
	
	public List<Product> getBestProductList();
	
	public Product getProduct(int pseq);
	
	public List<Product> getProductListByKind(String kind);
	
	public List<Product> getAllProducts(String name);
	
	public void insertProduct(Product vo);
	
	public void updateProduct(Product vo);
	
	public Page<Product> getAllProductsByName(String name, int page, int size);

}
