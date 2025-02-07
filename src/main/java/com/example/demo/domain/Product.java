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
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pseq;
	private String name;
	private String kind;
	private int price1;
	private int price2;
	private int price3;
	private String content;
	@Column(columnDefinition="varchar2(255) default 'default.jpg' ")
	private String image;
	@Column(columnDefinition="char(1) default 'y' ")
	private String useyn;
	@Column(columnDefinition="char(1) default 'n' ")
	private String bestyn;
	@Temporal(value=TemporalType.TIMESTAMP)
	@ColumnDefault("sysdate")
	private Date regdate;
	
	public Product(String name, String kind, int price1, int price2, int price3, String content, String image,
			String useyn, String bestyn, Date regdate) {		
		this.name = name;
		this.kind = kind;
		this.price1 = price1;
		this.price2 = price2;
		this.price3 = price3;
		this.content = content;
		this.image = image;
		this.useyn = useyn;
		this.bestyn = bestyn;
		this.regdate = regdate;
	}
	
	
}
