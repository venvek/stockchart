package com.jucha.stockchart;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MarketSummaryController {

	private static final Map<String, String> INDICES = Map.of(
	        "S&P 500", "^GSPC",
	        "NASDAQ", "^IXIC",
	        "Dow Jones", "^DJI"
	    );
	
	@GetMapping("/market-summary")
    public String marketSummary(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> indexList = new ArrayList<>();

        for (var entry : INDICES.entrySet()) {
            String name = entry.getKey();
            String ticker = entry.getValue();

            try {
                String url = String.format(
                    "https://query1.finance.yahoo.com/v8/finance/chart/%s?interval=1d&range=7d",
                    ticker
                );

                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode result = root.path("chart").path("result").get(0);
                JsonNode closes = result.path("indicators").path("quote").get(0).path("close");

                // 최근 종가 5개
                List<Double> trend = new ArrayList<>();
                for (JsonNode node : closes) {
                    if (!node.isNull()) trend.add(node.asDouble());
                }

                double current = trend.get(trend.size() - 1);
                double prev = trend.get(trend.size() - 2);
                double change = Math.round((current - prev) / prev * 10000.0) / 100.0; // %
                trend = trend.subList(trend.size() - 5, trend.size());

                Map<String, Object> data = new HashMap<>();
                data.put("name", name);
                data.put("value", Math.round(current * 100.0) / 100.0);
                data.put("change", change);
                data.put("trend", trend);

                indexList.add(data);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        model.addAttribute("indices", indexList);
        model.addAttribute("updated", new Date().toString());
        return "market-summary";
    }
	
		/*
		 * @GetMapping("/market-summary") public String marketSummary(Model model) { try
		 * { // static/json/market_summary.json 파일 읽기 File jsonFile = new
		 * File("src/main/resources/static/json/market_summary.json"); ObjectMapper
		 * mapper = new ObjectMapper(); Map<String, Object> jsonData =
		 * mapper.readValue(jsonFile, Map.class);
		 * 
		 * model.addAttribute("updated", jsonData.get("updated"));
		 * model.addAttribute("indices", jsonData.get("indices")); } catch (Exception e)
		 * { e.printStackTrace(); model.addAttribute("error", "데이터를 불러오지 못했습니다."); }
		 * 
		 * return "market-summary"; }
		 */
}