package com.jucha.stockchart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import DTO.StockDataDTO;
import Entity.Stock_Data;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockDataService {

	private final StockDataRepo repository;

	 @Autowired
	    public StockDataService(StockDataRepo repository) {
	        this.repository = repository;
	    }

		/*
		 * public List<StockDataDTO> getData(String ticker) { List<Stock_Data>
		 * stockDataList = repository.findByCompany_TickerOrderByDateAsc(ticker);
		 * List<StockDataDTO> stockDataDTOList = stockDataList.stream() .map(stock_Data
		 * -> new StockDataDTO(stock_Data.getDate(), stock_Data.getOpen(),
		 * stock_Data.getHigh(), stock_Data.getLow(), stock_Data.getClose(),
		 * stock_Data.getVolume(), stock_Data.getDividends(),
		 * stock_Data.getStockSplits(), stock_Data.getTicker()))
		 * .collect(Collectors.toList()); return stockDataDTOList;
		 * 
		 * }
		 */
	public List<Stock_Data> getStockDataByTicker(String ticker) {
        return repository.findByTickerOrderByDateAsc(ticker);
    }
}
