package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.ProductComment;

public interface CommentService {
	
	public void saveComment(ProductComment comment);
	
	public List<ProductComment> getCommentList(int pseq);
	
	public int getCountCommentList(int pseq);
}
