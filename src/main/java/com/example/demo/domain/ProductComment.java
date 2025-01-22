package com.example.demo.domain;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class ProductComment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int comment_seq;
	
	@ManyToOne
	@JoinColumn(name="pseq", nullable=false)
	private Product product;
	
	private String content;
	
	@ManyToOne
	@JoinColumn(name="id", nullable=false)
	private Member member;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@ColumnDefault("sysdate")
	private Date regdate;  // 상품평 등록일
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@ColumnDefault("sysdate")
	@Column(insertable=false)
	private Date modifydate;  // 상품평 수정일

}
