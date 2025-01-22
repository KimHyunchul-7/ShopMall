package com.example.demo.domain;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate  // 변경되는 컬럼만 수정할 수 있도록 함.
@Entity  // @Entity는 최종으로 입력해 준다.
public class Admin {
	@Id
	private String id;
	private String pwd;
	private String name;
	private String phone;
	
	

}
