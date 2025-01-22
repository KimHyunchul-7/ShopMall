package com.example.demo.domain;

import java.util.Date;
import java.util.List;

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
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class OrderDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// IDENTITY: JPA에서 각 DB에 맞게 시퀀스를 알아서 만들어 줌, AUTO: DB에서 시퀀스를 자동으로 만들어 줌
	private int odseq;
	
	@ManyToOne
	@JoinColumn(name="oseq", nullable=false)
	private Orders order;
	
	@ManyToOne
	@JoinColumn(name="pseq", nullable=false)
	private Product product;
	
	private int quantity;
	
	@ColumnDefault("1")
	private String result;  // 결제/배송 처리 여부
}
