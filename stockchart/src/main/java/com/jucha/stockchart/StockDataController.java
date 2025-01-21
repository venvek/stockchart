package com.jucha.stockchart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/stock-data")
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;
    
    @Autowired
    private CompanyService companyService;
  
    @GetMapping("/main")
    public String Mainpage() {
    	return "mainpage";
    }
    
    @GetMapping("/login")
    public String loginpage() {
    	return "login";
    }
    
    
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
    
    @GetMapping("/sector")
    public String sectorpage() {
    	return "sector";
    }
    
    @GetMapping("/explain")
    public String explainpage() {
    	return "explain";
    }
    
    @GetMapping("/enquiry")
    public String enquirypage() {
    	return "enquiry";
    }
    
    @GetMapping("/stocks/{ticker}") // API 요청을 위한 경로
    @ResponseBody
    public Map<String, Object> getStockDataJson(@PathVariable String ticker) {
        List<Stock_Data> stockDataList = stockDataService.getStockDataByTicker(ticker);
        List<Indicators> indicatorList = stockDataService.getindicatorsByTicker(ticker);

        // 데이터 응답을 JSON 형태로 반환
        Map<String, Object> response = new HashMap<>();
        response.put("stockDataList", stockDataList);
        response.put("indicatorList", indicatorList);
        response.put("ticker", ticker);

        return response;
    }
    
    @GetMapping("/suggestion")
    public List<String> getSuggestions(@RequestParam String ticker) {
        // 입력된 검색어로 필터링된 결과 반환
        return companyService.getSuggetsions(ticker);
    }
    
    
}
