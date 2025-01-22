package com.example.demo.persistence;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Member;

@SpringBootTest
public class MemberRepositoryTest {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Disabled
	@Test
	public void memberInsert() {
		Member member1 = Member.builder()
				.id("one")
				.pwd("1111")
				.name("김나리")
				.email("kimnari@naver.com")
				.zip_num("133-110")
				.address("서울시성동구성수동1가 1번지21호")
				.phone("010-777-7777")
				.regdate(new Date())
				.useyn("y")
				.build();
		
		memberRepo.save(member1);
		
		Member member2 = Member.builder()
				.id("two")
				.pwd("2222")
				.name("이백합")
				.email("leebaekhap@naver.com")
				.zip_num("130-120")
				.address("서울시송파구잠실2동 리센츠 아파트 201동 505호")
				.phone("011-123-4567")
				.regdate(new Date())
				.useyn("y")
				.build();
		
		memberRepo.save(member2);
	}
	
	@Disabled
	@Test
	public void testGetMember() {
		Optional<Member> data = memberRepo.findById("one");
		if(data.isEmpty()) {
			System.out.println("아이디가 존재하지 않습니다.");
		} else {
			Member member = data.get();
			
			System.out.println(member);
		}
		
	}
	

}
