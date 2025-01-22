package com.example.demo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class Orders {
	@Id
	private int oseq;
	
	@ManyToOne
	@JoinColumn(name="id", nullable=false)
	private Member member;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@ColumnDefault("sysdate")
	private Date indate;
	
	@OneToMany(mappedBy="order", fetch=FetchType.EAGER) // EAGER: Orders를 주문하면 OrderDetail도 같이 조회할 수 있도록 해줌.
	@ToString.Exclude
	private List<OrderDetail> orderDetail = new ArrayList<OrderDetail>();
}
