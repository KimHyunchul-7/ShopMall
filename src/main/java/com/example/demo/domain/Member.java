package com.example.demo.domain;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@DynamicUpdate       // 필요한 값만 update
@Entity
public class Member {
	@Id
	private String id;
	private String pwd;
	private String name;
	private String email;
	private String zip_num;
	private String address;
	private String phone;
	@Column(columnDefinition="char(1) default 'y'")
	private String useyn;
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(columnDefinition="DATE default sysdate")
	private Date regdate;
}
