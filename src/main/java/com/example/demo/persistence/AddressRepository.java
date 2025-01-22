package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Address;
import com.example.demo.domain.Product;

public interface AddressRepository extends JpaRepository<Address, String> {
	
	// 동이름을 조건으로 주소목록 검색
	public List<Address> findByDongContaining(String dong);
}
