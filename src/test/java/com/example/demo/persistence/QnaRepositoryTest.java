package com.example.demo.persistence;

import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Member;
import com.example.demo.domain.Qna;

@SpringBootTest
public class QnaRepositoryTest {
	
	@Autowired
	private QnaRepository qnaRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Disabled
	@Test
	public void testQnaInsert() {
		Member member = memberRepo.findById("one").get();
		
		Qna qna1 = Qna.builder()
				.subject("Qna 테스트")
				.content("질문내용 1")
				.reply("답변 드립니다.")
				.rep("2")
				.indate(new Date())
				.member(member)
				.build();
		qnaRepo.save(qna1);
		
		Qna qna2 = Qna.builder()
				.subject("배송 문의")
				.content("언제 배송되나요?")
				.indate(new Date())
				.member(member)
				.build();
		qnaRepo.save(qna2);
	}

}
