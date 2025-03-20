package com.jucha.stockchart;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CompanyRepository extends JpaRepository<Company, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.
    Company findByTicker(String ticker);
    
    @Query("SELECT c.ticker FROM Company c WHERE c.ticker LIKE %:query% OR c.name LIKE %:query%")
    List<String> findTickersByQuery(@Param("query") String query);
    
    @Query("SELECT c.ticker, c.name, c.marketCap, c.previousClose, c.open, c.dayLow, c.dayHigh, c.volume " +
    	       "FROM Company c")
    List<Object[]> findLatestStockData();

    // 일별 데이터 (기본)
    @Query("SELECT s FROM StockData s WHERE s.ticker = :ticker ORDER BY s.date ASC")
    List<Stock_Data> findDailyData(@Param("ticker") String ticker);

    // 주별 평균 데이터
    @Query(value = "SELECT ticker, DATE_FORMAT(date, '%Y-%u') AS week, " +
                   "AVG(close) as close FROM stock_data " +
                   "WHERE ticker = :ticker GROUP BY week ORDER BY week ASC", nativeQuery = true)
    List<Map<String, Object>> findWeeklyData(@Param("ticker") String ticker);

    // 월별 평균 데이터
    @Query(value = "SELECT ticker, DATE_FORMAT(date, '%Y-%m') AS month, " +
                   "AVG(close) as close FROM stock_data " +
                   "WHERE ticker = :ticker GROUP BY month ORDER BY month ASC", nativeQuery = true)
    List<Map<String, Object>> findMonthlyData(@Param("ticker") String ticker);

    // 연도별 평균 데이터
    @Query(value = "SELECT ticker, DATE_FORMAT(date, '%Y') AS year, " +
                   "AVG(close) as close FROM stock_data " +
                   "WHERE ticker = :ticker GROUP BY year ORDER BY year ASC", nativeQuery = true)
    List<Map<String, Object>> findYearlyData(@Param("ticker") String ticker);
}
