package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Address {
	@Id
	private String zipcode;
	private String zip_num;
	private String sido;
	private String gugun;
	private String dong;
	private String bunji;
}
