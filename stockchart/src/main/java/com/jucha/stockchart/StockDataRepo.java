package com.jucha.stockchart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDataRepo extends JpaRepository<Stock_Data, Long> {
	List<Stock_Data> findByCompany_TickerOrderByDateAsc(String ticker);
	
	List<Stock_Data> findByTicker(String ticker);
}
