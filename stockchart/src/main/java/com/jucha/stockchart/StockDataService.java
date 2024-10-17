package com.jucha.stockchart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@Service
public class StockDataService {

	@Autowired
	private StockDataRepository stockDataRepository;

	public List<StockData> getStockDataByTicker(String ticker) {
		return stockDataRepository.findByTickerOrderByDateAsc(ticker);
	}

	public List<StockData> getStockData(String ticker) {

		return stockDataRepository.findByTicker(ticker); // Custom query method
	}
}
