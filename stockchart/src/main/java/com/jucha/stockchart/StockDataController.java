package com.jucha.stockchart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
