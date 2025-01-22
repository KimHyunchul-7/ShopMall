package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Qna;

public interface QnaRepository extends JpaRepository<Qna, Integer> {
	
	// 사용자별 문의사항 목록 조회 (JPQL 사용)
	@Query("SELECT q FROM Qna q WHERE q.member.id=%?1% ORDER BY q.qseq DESC")
	public List<Qna> getQnaList(String id);
	
	

}
