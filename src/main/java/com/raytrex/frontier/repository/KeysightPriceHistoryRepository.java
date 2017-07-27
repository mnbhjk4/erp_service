package com.raytrex.frontier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.KeysightPriceHistory;

public interface KeysightPriceHistoryRepository extends JpaRepository<KeysightPriceHistory, String> {
}
