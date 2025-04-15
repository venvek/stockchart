package com.jucha.stockchart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StockDataService {
	
	@Autowired
	private StockDataRepo repository;
	
	@Autowired
    private IndicatorRepository indicatorsRepo;


	public List<Stock_Data> getStockDataByTicker(String ticker) {
        return repository.findByCompany_TickerOrderByDateAsc(ticker);
    }
	
	public List<Indicators> getindicatorsByTicker(String ticker) {
		return indicatorsRepo.findByCompany_TickerOrderByDateAsc(ticker);
	}
	
	public StockIndicatorResponse getStockAndIndicators(String ticker) {
        List<Stock_Data> stockData = repository.findByCompany_TickerOrderByDateAsc(ticker);
        List<Indicators> indicators = indicatorsRepo.findByCompany_TickerOrderByDateAsc(ticker);

        // 주식 데이터와 지표 데이터를 하나의 응답 형태로 결합할 수 있습니다.
        return new StockIndicatorResponse(stockData, indicators);
    }
	
	public List<Stock_Data> getStockData(String ticker) {
        return repository.findByTicker(ticker);
    }
	
	public List<?> getStockDataByPeriod(String ticker, String period) {
        switch (period) {
        	case "W":
        		return repository.findWeeklyData(ticker);
            case "M":
                return repository.findMonthlyData(ticker);  // DTO 반환
            case "Y":
                return repository.findYearlyData(ticker);  // DTO 반환
            default:
                return repository.findDailyData(ticker);  // StockData 반환
        }
    }
	
	public List<Stock_Data> filtervolume(String volume) {
		return repository.scanStocks(null, null);
		
	}
}

