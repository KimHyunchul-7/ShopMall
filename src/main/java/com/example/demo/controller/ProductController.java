package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/product_detail")
	public String productDetailView(Product vo, Model model) {
		Product product = productService.getProduct(vo.getPseq());
		
		model.addAttribute("productVO", product);
		
		return "product/productDetail";		
	}
	
	@GetMapping("/category")
	public String productKindAction(Product vo, Model model) {
		List<Product> kindList = productService.getProductListByKind(vo.getKind());
		
		model.addAttribute("productKindList", kindList);
		
		return "product/productKind";		
	}
	

}
