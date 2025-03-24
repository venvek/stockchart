package com.jucha.stockchart;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDataRepo extends JpaRepository<Stock_Data, Long> {
	List<Stock_Data> findByCompany_TickerOrderByDateAsc(String ticker);
	
	List<Stock_Data> findByTicker(String ticker);
	
	// 일별 데이터 (기본)
    @Query("SELECT s FROM StockData s WHERE s.ticker = :ticker ORDER BY s.date ASC")
    List<Stock_Data> findDailyData(@Param("ticker") String ticker);

    // 주별 평균 데이터
    @Query(value = "SELECT ticker, DATE_FORMAT(date, '%Y-%u') AS week, " +
                   "AVG(close) as close FROM stock_data " +
                   "WHERE ticker = :ticker GROUP BY week ORDER BY week ASC", nativeQuery = true)
    List<Map<String, Object>> findWeeklyData(@Param("ticker") String ticker);

	/*
	 * // 월별 평균 데이터
	 * 
	 * @Query(value = "SELECT ticker, DATE_FORMAT(date, '%Y-%m') AS month, " +
	 * "AVG(close) as close FROM stock_data " +
	 * "WHERE ticker = :ticker GROUP BY month ORDER BY month ASC", nativeQuery =
	 * true) List<Map<String, Object>> findMonthlyData(@Param("ticker") String
	 * ticker);
	 * 
	 * // 연도별 평균 데이터
	 * 
	 * @Query(value = "SELECT ticker, DATE_FORMAT(date, '%Y') AS year, " +
	 * "AVG(close) as close FROM stock_data " +
	 * "WHERE ticker = :ticker GROUP BY year ORDER BY year ASC", nativeQuery = true)
	 * List<Map<String, Object>> findYearlyData(@Param("ticker") String ticker);
	 */
    
    @Query(value = "SELECT DATE_FORMAT(date, '%Y-%m') AS period, AVG(close) AS avgClose " +
                   "FROM stock_data WHERE ticker = :ticker GROUP BY period ORDER BY period ASC", 
                   nativeQuery = true)
    List<AggregatedStockData> findMonthlyData(@Param("ticker") String ticker);

    @Query(value = "SELECT DATE_FORMAT(date, '%Y') AS period, AVG(close) AS avgClose " +
                   "FROM stock_data WHERE ticker = :ticker GROUP BY period ORDER BY period ASC", 
                   nativeQuery = true)
    List<AggregatedStockData> findYearlyData(@Param("ticker") String ticker);
    
    
}
