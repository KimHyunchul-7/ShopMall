package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.Member;

import jakarta.transaction.Transactional;

public interface MemberRepository extends JpaRepository<Member, String> {
	
	// Name과 Email로 id 조회
	public Member findByNameAndEmail(String name, String email);
	
	// Id와 Name과 Email로 pwd 조회
	public Member findByIdAndNameAndEmail(String id, String name, String email);
	
	@Transactional  // 커밋, 롤백 처리
	@Modifying  // @Query와 UPDATE 사용할때 사용하는 어노테이션임
	// @Query(value="UPDATE member SET pwd=%?2% WHERE id=%?1%", nativeQuery=true)
	// public void changePassword(String id, String pwd);
	@Query(value="UPDATE member SET pwd=:pwd WHERE id=:id", nativeQuery=true)	
	public void changePassword(@Param("id") String id, @Param("pwd") String pwd);

	// 회원명을 조건으로 회원목록 조회
	public List<Member> findMemberByNameContaining(String name);
}
