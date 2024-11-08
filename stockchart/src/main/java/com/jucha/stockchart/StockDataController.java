package com.jucha.stockchart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock-data")
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
    public List<Stock_Data> getStockData2(@PathVariable String ticker) {
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
    
    @GetMapping("/stocks/{ticker}") // API 요청을 위한 경로
    public ResponseEntity<?> getStockData(@PathVariable String ticker) {
        // 주식 데이터와 지표 데이터를 가져옴
        List<Stock_Data> stockDataList = stockDataService.getStockDataByTicker(ticker);
        List<Indicators> indicatorList = stockDataService.getindicatorsByTicker(ticker);

        Map<String, Object> response = new HashMap<>();
        response.put("stockDataList", stockDataList);
        response.put("indicatorList", indicatorList);
        response.put("ticker", ticker);

        return ResponseEntity.ok(response); // JSON 형식으로 응답
    }
    
}
