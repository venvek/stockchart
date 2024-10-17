package com.jucha.stockchart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;


    @GetMapping("/chart")
    public String showChart(@RequestParam("ticker") String ticker, Model model) {
        List<StockData> stockDataList = stockDataService.getStockDataByTicker(ticker);
        model.addAttribute("ticker", ticker);
        model.addAttribute("stockData", stockDataList);
        return "stockchart"; // 리턴할 뷰 이름, 필요 시 HTML 뷰 추가
    }
    
    @GetMapping("/stock-data")
    public String getStockData(@RequestParam String ticker, Model model) {
    	List<StockData> stockDataList = stockDataService.getStockDataByTicker(ticker);
        // Format dates for the chart
        stockDataList.forEach(stock -> {
            stock.setFormattedDate(new SimpleDateFormat("yyyy-MM-dd").format(stock.getDate()));
        });
        model.addAttribute("stockDataList", stockDataList);
        model.addAttribute("ticker", ticker);
        return "chart"; // Thymeleaf 템플릿 이름
    }
    
    @GetMapping("/api/stock-data")
    @ResponseBody
    public List<StockData> getStockDataApi(@RequestParam String ticker) {
        return stockDataService.getStockDataByTicker(ticker);
    }
    
    @GetMapping("/aapl") // Adjust the path as needed
    public List<StockData> getStockData() {
        return stockDataService.getStockData("AAPL"); // Replace with your logic
    }
    
    @GetMapping("/api/stocks/{ticker}")
    public List<StockData> getStockData(@PathVariable String ticker) {
        return stockDataService.getStockData(ticker);
    }
    
    @GetMapping("/")
    public String Mainpage() {
    	return "mainpage";
    }
    
}
