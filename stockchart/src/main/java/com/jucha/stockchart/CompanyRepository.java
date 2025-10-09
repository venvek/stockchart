package com.jucha.stockchart;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    
    @Query("SELECT s.close FROM Stock_Data s WHERE s.company.ticker = :ticker ORDER BY s.date DESC")
    List<BigDecimal> findLatestCloseByTicker(@Param("ticker") String ticker, Pageable pageable);
    
    List<Company> findTop10ByTickerContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(String ticker, String companyName);
    
	/*
	 * @Query("SELECT new com.example.dto.HeatmapDto(c.name, c.ticker, c.sector, c.marketCap, "
	 * + "((c.open - c.previousClose) / c.previousClose * 100)) FROM Company c " +
	 * "WHERE c.marketCap IS NOT NULL AND c.previousClose > 0") List<HeatMapData>
	 * getHeatmapData();
	 */
    
}
