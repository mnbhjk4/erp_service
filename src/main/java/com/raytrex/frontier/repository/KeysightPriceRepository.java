package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.raytrex.frontier.repository.bean.KeysightPrice;

public interface KeysightPriceRepository extends JpaRepository<KeysightPrice, String> {
	@Query("SELECT kp FROM KeysightPrice kp WHERE kp.productNo like ?1")
	public List<KeysightPrice> findAllByProductNoLike(String key);
}
