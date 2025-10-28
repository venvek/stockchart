package com.jucha.stockchart;

import java.io.File;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MarketSummaryController {

    @GetMapping("/market-summary")
    public String marketSummary(Model model) {
        try {
            // static/json/market_summary.json 파일 읽기
            File jsonFile = new File("src/main/resources/static/json/market_summary.json");
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonData = mapper.readValue(jsonFile, Map.class);

            model.addAttribute("updated", jsonData.get("updated"));
            model.addAttribute("indices", jsonData.get("indices"));
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "데이터를 불러오지 못했습니다.");
        }

        return "market-summary";
    }
}