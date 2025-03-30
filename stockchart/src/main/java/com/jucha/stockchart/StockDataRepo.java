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
	
	@Query("SELECT s FROM Stock_Data s WHERE s.company.ticker = :ticker ORDER BY s.date ASC")
	List<Stock_Data> findDailyData(@Param("ticker") String ticker);

	// 주별 평균 데이터 (Native Query)
	@Query(value = "SELECT c.ticker, DATE_FORMAT(s.date, '%Y-%u') AS week, " +
	               "AVG(s.close) as close FROM stock_data s " +
	               "JOIN company c ON s.ticker = c.ticker " +
	               "WHERE c.ticker = :ticker GROUP BY week ORDER BY week ASC", 
	       nativeQuery = true)
	List<Map<String, Object>> findWeeklyData(@Param("ticker") String ticker);

	// 월별 평균 데이터 (Native Query)
	@Query(value = "SELECT DATE_FORMAT(s.date, '%Y-%m') AS period, AVG(s.close) AS avgClose " +
	               "FROM stock_data s " +
	               "JOIN company c ON s.ticker = c.ticker " +
	               "WHERE c.ticker = :ticker GROUP BY period ORDER BY period ASC", 
	       nativeQuery = true)
	List<Map<String, Object>> findMonthlyData(@Param("ticker") String ticker);

	// 연도별 평균 데이터 (Native Query)
	@Query(value = "SELECT DATE_FORMAT(s.date, '%Y') AS period, AVG(s.close) AS avgClose " +
	               "FROM stock_data s " +
	               "JOIN company c ON s.ticker = c.ticker " +
	               "WHERE c.ticker = :ticker GROUP BY period ORDER BY period ASC", 
	       nativeQuery = true)
	List<Map<String, Object>> findYearlyData(@Param("ticker") String ticker);
    
    
}
