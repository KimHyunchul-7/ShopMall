package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Qna;
import com.example.demo.persistence.QnaRepository;

@Service
public class QnaServiceImpl implements QnaService {
	
	@Autowired
	private QnaRepository qnaRepo;
	
	@Override
	public void insertQna(Qna qna) {
	
		qnaRepo.save(qna);
	}
	
	@Override
	public Qna getQna(int qseq) {
		
		return qnaRepo.findById(qseq).get();
	}
	
	@Override
	public List<Qna> getqnaList(String id) {
		 
		return qnaRepo.getQnaList(id);
	}

	@Override
	public List<Qna> getAllQna() {
		
		return qnaRepo.findAll();
	}

	@Override
	public void updateQna(Qna vo) {
		Optional<Qna> result = qnaRepo.findById(vo.getQseq());
		
		if(result.isPresent()) {
			Qna qna = result.get();
			
			qna.setReply(vo.getReply());
			qna.setRep("2");
			
			qnaRepo.save(qna);
		}
		
	}

}
