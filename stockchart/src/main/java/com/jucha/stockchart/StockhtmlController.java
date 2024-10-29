package com.jucha.stockchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/stock-data")
public class StockhtmlController {

	@Autowired
	private StockDataService stockDataService;

	@GetMapping("/{ticker}")
	public String showStockChart(@PathVariable String ticker, Model model) {
		List<Stock_Data> stockDataList = stockDataService.getStockDataByTicker(ticker);

		model.addAttribute("stockDataList", stockDataList);
		model.addAttribute("ticker", ticker);
		return "char";
	}
	
	@GetMapping("/candle/{ticker}")
	public String showStockcandleChart(@PathVariable String ticker, Model model) {
		List<Stock_Data> stockDataList = stockDataService.getStockDataByTicker(ticker);

		model.addAttribute("stockDataList", stockDataList);
		model.addAttribute("ticker", ticker);
		return "candlechart";
	}
}