package com.jucha.stockchart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDataRepository extends JpaRepository<StockData, Integer> {
    List<StockData> findAllByTicker(String ticker);
    List<StockData> findByTickerOrderByDateAsc(String ticker);
}