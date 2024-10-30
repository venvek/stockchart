package com.jucha.stockchart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndicatorRepository extends JpaRepository<Indicators, Long> {
	List<Indicators> findByCompany_TickerOrderByDateAsc(String ticker);
}
