package com.jucha.stockchart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StockhtmlController {

    @GetMapping("/stock-chart/{ticker}")
    public String showStockChart(@PathVariable String ticker, Model model) {
        model.addAttribute("ticker", ticker);  // 티커를 HTML로 전달
        return "char";  // stockChart.html 파일을 반환
    }
}