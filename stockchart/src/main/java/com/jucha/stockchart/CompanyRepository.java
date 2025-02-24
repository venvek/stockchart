package com.jucha.stockchart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CompanyRepository extends JpaRepository<Company, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.
    Company findByTicker(String ticker);
    
    @Query("SELECT c.ticker FROM Company c WHERE c.ticker LIKE %:query% OR c.name LIKE %:query%")
    List<String> findTickersByQuery(@Param("query") String query);
    
    @Query("SELECT c.ticker, c.name, s.close, s.open FROM Company c " +
    	       "JOIN StockData s ON c.ticker = s.ticker " +
    	       "WHERE s.date = (SELECT MAX(s2.date) FROM StockData s2 WHERE s2.ticker = c.ticker)")
    	List<Object[]> findLatestStockData();

}
