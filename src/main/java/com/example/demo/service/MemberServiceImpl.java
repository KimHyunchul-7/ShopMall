package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Address;
import com.example.demo.domain.Member;
import com.example.demo.persistence.AddressRepository;
import com.example.demo.persistence.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private AddressRepository addrRepo;
	
	// 회원 로그인
	// 리턴값: 1 - ID, Pwd 일치, 0: Pwd 불일치, -1: ID가 존재하지 않음.
	@Override
	public int loginId(Member vo) {
		int result = -1;
		
		Optional<Member> member = memberRepo.findById(vo.getId());
		
		if(member.isEmpty()) {
			result = -1;
		} else if(vo.getPwd().equals(member.get().getPwd())) {
			result = 1;	// id, pwd가 모두 일치	
		} else {
			result = 0; // 비밀번호 불일치			
		}
		
		return result;
	}

	// 회원 ID 확인
	// id가 존재하면 1, 존재하지 않으면 -1
	@Override
	public int confirmId(String id) {
		
		Optional<Member> member = memberRepo.findById(id);
		
		if(member.isPresent()) {
			return 1;
		} else {
			return -1;
		}		
	}

	// 회원정보 상세 조회
	@Override
	public Member getMember(String id) {

		return memberRepo.findById(id).get();
	}

	@Override
	public void insertMember(Member vo) {
		
		memberRepo.save(vo);		
	}

	@Override
	public List<Address> getAddressByDong(String dong) {
		
		return addrRepo.findByDongContaining(dong);
	}

	@Override
	public Member getIdByNameAndEmail(String name, String email) {
		
		return memberRepo.findByNameAndEmail(name, email);
	}

	@Override
	public Member getPwdByIdNameEmail(String id, String name, String email) {

		return memberRepo.findByIdAndNameAndEmail(id, name, email);
	}

	@Override
	public void changePassword(Member vo) {
		
		memberRepo.changePassword(vo.getId(), vo.getPwd());		
	}

	@Override
	public List<Member> getMemberList(String name) {
		
		return memberRepo.findMemberByNameContaining(name);
	}

}
