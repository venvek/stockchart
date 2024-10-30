package com.jucha.stockchart;

import java.util.List;

public class StockIndicatorResponse {

	 private List<Stock_Data> stockData;
	    private List<Indicators> indicators;

	    public StockIndicatorResponse(List<Stock_Data> stockData, List<Indicators> indicators) {
	        this.stockData = stockData;
	        this.indicators = indicators;
	    }

		public List<Stock_Data> getStockData() {
			return stockData;
		}

		public void setStockData(List<Stock_Data> stockData) {
			this.stockData = stockData;
		}

		public List<Indicators> getIndicators() {
			return indicators;
		}

		public void setIndicators(List<Indicators> indicators) {
			this.indicators = indicators;
		}
	    
	    
}
