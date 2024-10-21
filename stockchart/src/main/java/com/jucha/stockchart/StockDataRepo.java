package com.jucha.stockchart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Entity.Stock_Data;

@Repository
public interface StockDataRepo extends JpaRepository<Stock_Data, Long> {
	List<Stock_Data> findByTickerOrderByDateAsc(String ticker);
}
