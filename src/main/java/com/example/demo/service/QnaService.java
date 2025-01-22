package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Qna;

public interface QnaService {

	void insertQna(Qna qna);

	Qna getQna(int qseq);

	List<Qna> getqnaList(String id);
	
	List<Qna> getAllQna();
	
	void updateQna(Qna vo);

}