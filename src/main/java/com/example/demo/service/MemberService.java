package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Address;
import com.example.demo.domain.Member;

public interface MemberService {
	
	// 회원 로그인
	// 리턴값: 1 - ID, 0: Pwd 불일치, -1: ID가 존재하지 않음. 
	public int loginId(Member vo);
	
	// 회원 ID 확인
	// id가 존재하면 1, 존재하지 않으면 -1
	public int confirmId(String id);
	
	// 회원정보 상세 조회
	public Member getMember(String id);
	
	// 회원정보 저장
	public void insertMember(Member vo);
	
	// 동이름으로 주소찾기
	public List<Address> getAddressByDong(String dong);
	
	// Name과 Email로 id 찾기
	public Member getIdByNameAndEmail(String name, String email);
	
	// Id와 Name과 Email로 pwd 찾기
	public Member getPwdByIdNameEmail(String id, String name, String email);
	
	public void changePassword(Member vo);
	
	// 전체 회원 조회
	public List<Member> getMemberList(String name);
	
	

}
