package com.jucha.stockchart;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;

  
    @GetMapping("/main")
    public String Mainpage() {
    	return "mainpage";
    }
    
    @GetMapping("/login")
    public String loginpage() {
    	return "login";
    }
    
	/*
	 * @GetMapping("/{ticker}") public List<StockDataDTO> getStockData(@PathVariable
	 * String ticker) { return stockDataService.getData(ticker); }
	 */
    
    @GetMapping("/chart/{ticker}")
    public List<Stock_Data> getStockData(@PathVariable String ticker) {
        return stockDataService.getStockDataByTicker(ticker);
    }
    
    @GetMapping("/join")
    public String joinpage() {
    	return "joinpage";
    }
    
    @GetMapping("/mypage")
    public String mypage() {
    	return "mypage";
    }
    
    @GetMapping("/admin")
    public String adminpage() {
    	return "adminpage";
    }
    
    
}
