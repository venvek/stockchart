package com.jucha.stockchart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import DTO.StockDataDTO;
import Entity.Stock_Data;

import java.util.List;

@RestController
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;

  
    @GetMapping("/")
    public String Mainpage() {
    	return "mainpage";
    }
    
    @GetMapping("login")
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
}
