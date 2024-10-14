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
  
        public void createChart(String ticker) {
            List<StockData> dataList = stockDataRepository.findByTickerOrderByDateAsc(ticker);

            TimeSeries closePrices = new TimeSeries("Close Price");
            TimeSeries volumes = new TimeSeries("Volume");

            for (StockData stockData : dataList) {
                closePrices.add(new Second(stockData.getDate()), stockData.getClose());
                volumes.add(new Second(stockData.getDate()), stockData.getVolume());
            }

            TimeSeriesCollection dataset = new TimeSeriesCollection();
            dataset.addSeries(closePrices);
            dataset.addSeries(volumes);

            JFreeChart chart = ChartFactory.createTimeSeriesChart(
                    "Stock Price and Volume for " + ticker,
                    "Date",
                    "Price / Volume",
                    dataset,
                    true,
                    true,
                    false
            );

            SwingUtilities.invokeLater(() -> {
                ChartPanel panel = new ChartPanel(chart);
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
            });
        }
}

