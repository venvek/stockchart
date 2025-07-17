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

import java.util.ArrayList;
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
    
    
    @GetMapping("/explain")
    public String explainpage() {
    	return "explain";
    }
    
    @GetMapping("/enquiry")
    public String enquirypage() {
    	return "enquiry";
    }
    
    @GetMapping("/recommend")
    public String recommendpage() {
    	return "recommend";
    }
    
    @GetMapping("/heatmap")
    public String heatmappage() {
    	return "heatmap";
    }
    
    @GetMapping("/ohlc")
    public String ohlcpage() {
    	return "newchart";
    }
    
    @GetMapping("/lay")
    public String layoutpage() {
    	return "layout";
    }
    
	/*
	 * @GetMapping("/stocks/{ticker}") // API 요청을 위한 경로
	 * 
	 * @ResponseBody public Map<String, Object> getStockDataJson(@PathVariable
	 * String ticker) { List<Stock_Data> stockDataList =
	 * stockDataService.getStockDataByTicker(ticker); List<Indicators> indicatorList
	 * = stockDataService.getindicatorsByTicker(ticker);
	 * 
	 * // 데이터 응답을 JSON 형태로 반환 Map<String, Object> response = new HashMap<>();
	 * response.put("stockDataList", stockDataList); response.put("indicatorList",
	 * indicatorList); response.put("ticker", ticker);
	 * 
	 * return response; }
	 */
    
    
    @GetMapping("/stocks/{ticker}")
    @ResponseBody
    public Map<String, Object> getStockChartData(@PathVariable String ticker) {
        List<Stock_Data> stockDataList = stockDataService.getStockDataByTicker(ticker);
        System.out.println("불러온 데이터 수: " + stockDataList.size());
        System.out.println("요청받은 ticker: " + ticker);
        
        List<String> labels = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        List<Long> volumes = new ArrayList<>();
        
        for (Stock_Data data : stockDataList) {
            labels.add(data.getDate().toString());
            prices.add(data.getClose().doubleValue()); // 혹은 open/high/low 중 원하는 항목 close가 BigDecimal인데 List<Double>에 넣으려고 하면 IDE가 경고합니다.
            volumes.add(data.getVolume());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("prices", prices);
        response.put("volumes", volumes);
        
        return response;
    }
    
    @GetMapping("/companies")
    @ResponseBody
    public List<CompanyHeatmapDTO> getCompaniesForHeatmap() {
        return companyService.getCompaniesWithPriceChange();
    }
    
    @GetMapping("/api/heatmap")
    public List<HeatMapData> getHeatmapData() {
        return companyService.getHeatmapStocks();
    }
    
    
    
    @GetMapping("/sector")
    public String showSectorPage() {
        return "sector";
    }
    
	/*
	 * @GetMapping("/scan") public List<Stock_Data> scanStocks(
	 * 
	 * @RequestParam(name = "minVolume", required = false, defaultValue = "0") Long
	 * minVolume,
	 * 
	 * @RequestParam(name = "minClose", required = false, defaultValue = "0") Double
	 * minClose ) { return stockService.scanStocks(minVolume, minClose); }
	 */
    
	/*
	 * @GetMapping("/{ticker}") public List<?> getStockData(
	 * 
	 * @PathVariable String ticker,
	 * 
	 * @RequestParam(defaultValue = "D") String period // 기본값: 일별 ) { return
	 * stockService.getStockDataByPeriod(ticker, period); }
	 */
    
}
