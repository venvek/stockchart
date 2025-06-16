package com.jucha.stockchart;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDataRepo extends JpaRepository<Stock_Data, Long> {
	List<Stock_Data> findByCompany_TickerOrderByDateAsc(String ticker);
	
	@Query("SELECT s FROM Stock_Data s WHERE s.company.ticker = :ticker")
	List<Stock_Data> findByTicker(@Param("ticker") String ticker);
	
	@Query("SELECT s FROM Stock_Data s WHERE s.company.ticker = :ticker ORDER BY s.date ASC")
	List<Stock_Data> findDailyData(@Param("ticker") String ticker);

	// 주별 평균 데이터 
	@Query(value = "SELECT c.ticker, DATE_FORMAT(s.date, '%Y-%u') AS week, " +
	               "AVG(s.close) as close FROM stock_data s " +
	               "JOIN company c ON s.ticker = c.ticker " +
	               "WHERE c.ticker = :ticker GROUP BY week ORDER BY week ASC", 
	       nativeQuery = true)
	List<HeatMapData> findWeeklyData(@Param("ticker") String ticker);

	// 월별 평균 데이터 
	@Query(value = "SELECT DATE_FORMAT(s.date, '%Y-%m') AS period, AVG(s.close) AS avgClose " +
	               "FROM stock_data s " +
	               "JOIN company c ON s.ticker = c.ticker " +
	               "WHERE c.ticker = :ticker GROUP BY period ORDER BY period ASC", 
	       nativeQuery = true)
	List<HeatMapData> findMonthlyData(@Param("ticker") String ticker);

	// 연도별 평균 데이터 
	@Query(value = "SELECT DATE_FORMAT(s.date, '%Y') AS period, AVG(s.close) AS avgClose " +
	               "FROM stock_data s " +
	               "JOIN company c ON s.ticker = c.ticker " +
	               "WHERE c.ticker = :ticker GROUP BY period ORDER BY period ASC", 
	       nativeQuery = true)
	List<HeatMapData> findYearlyData(@Param("ticker") String ticker);
    
	@Query("SELECT s FROM Stock_Data s WHERE s.volume >= :minVolume AND s.close >= :minClose")
	List<Stock_Data> scanStocks(@Param("minVolume") Long minVolume, @Param("minClose") Double minClose);
    
	@Query("SELECT s.close FROM Stock_Data s WHERE s.ticker = :ticker ORDER BY s.date DESC")
	List<BigDecimal> findLatestCloseByTicker(@Param("ticker") String ticker, Pageable pageable);
	
}
