package com.jucha.stockchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StockhtmlController {

	@Autowired
	private StockDataService stockDataService;

	
	@GetMapping("/candle/{ticker}")
	public String showStockcandleChart(@PathVariable String ticker, Model model) {
		List<Stock_Data> stockDataList = stockDataService.getStockDataByTicker(ticker);

		model.addAttribute("stockDataList", stockDataList);
		model.addAttribute("ticker", ticker);
		return "candlechart";
	}
	
	@GetMapping("/stocks/{ticker}")
    public String getStockChart(@PathVariable String ticker, Model model) {
        // 주식 데이터와 지표 데이터를 가져옴
		List<Stock_Data> stockDataList = stockDataService.getStockDataByTicker(ticker);
	    List<Indicators> indicatorList = stockDataService.getindicatorsByTicker(ticker);

	    model.addAttribute("stockDataList", stockDataList);
	    model.addAttribute("indicatorList", indicatorList); 
        model.addAttribute("ticker", ticker);

        // Thymeleaf 템플릿으로 반환
        return "indicator";
    }
	
	/*
	 * @GetMapping("/stocks") public String getStockChart2(@RequestParam("ticker")
	 * String ticker, Model model) { List<Stock_Data> stockDataList =
	 * stockDataService.getStockDataByTicker(ticker); List<Indicators> indicatorList
	 * = stockDataService.getindicatorsByTicker(ticker);
	 * 
	 * model.addAttribute("stockDataList", stockDataList);
	 * model.addAttribute("indicatorList", indicatorList);
	 * model.addAttribute("ticker", ticker);
	 * 
	 * return "indicator"; }
	 */
	
	
}