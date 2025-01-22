package com.example.demo.domain;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor   // 기본생성자 생성
@AllArgsConstructor  // 모든 멤버를 매개변수로 하는 생성자
@DynamicInsert       // 필요한 값만 insert (default 값 사용시)
@DynamicUpdate       // 필요한 값만 updatge
@Entity
public class Qna {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int qseq;
	private String subject;  // 게시글 제목
	private String content;  // 질문 내용
	private String reply;    // 답변 내용
	@ManyToOne
	@JoinColumn(name="id", nullable=false)
	private Member member;   // 문의한 사용자 아이디
	@ColumnDefault("1")
	private String rep;      // 관리자의 답변 여부
	@ColumnDefault("sysdate")
	private Date indate;
	
	

}
