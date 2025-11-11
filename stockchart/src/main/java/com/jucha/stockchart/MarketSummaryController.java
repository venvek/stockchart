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

	private final MarketSummaryService marketSummaryService;

    // ✅ 여기서 타입을 MarketSummaryService 로 바꿔야 함
    public MarketSummaryController(MarketSummaryService marketSummaryService) {
        this.marketSummaryService = marketSummaryService;
    }

    @GetMapping("/market-summary")
    public String marketSummary(Model model) {
        List<Map<String, Object>> indices = marketSummaryService.fetchMarketIndices();
        model.addAttribute("indices", indices);
        model.addAttribute("updated", new java.util.Date());
        return "market-summary";
    }
	
	private static final Map<String, String> INDICES = Map.of(
	        "S&P 500", "^GSPC",
	        "NASDAQ", "^IXIC",
	        "Dow Jones", "^DJI",
	        "KOSPI", "^KS11" 
	    );
	
	@GetMapping("/market-dashboard")
    public String dashboard(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> indexList = new ArrayList<>();

        try {
            // 🔹 주요 지수 가져오기
            for (var entry : INDICES.entrySet()) {
                String name = entry.getKey();
                String ticker = entry.getValue();

                String url = String.format(
                    "https://query1.finance.yahoo.com/v8/finance/chart/%s?interval=1d&range=7d",
                    ticker
                );

                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode closes = root.path("chart").path("result").get(0)
                        .path("indicators").path("quote").get(0).path("close");

                List<Double> trend = new ArrayList<>();
                for (JsonNode node : closes)
                    if (!node.isNull()) trend.add(node.asDouble());

                if (trend.size() < 2) continue;
                double current = trend.get(trend.size() - 1);
                double prev = trend.get(trend.size() - 2);
                double change = Math.round((current - prev) / prev * 10000.0) / 100.0;

                trend = trend.subList(Math.max(trend.size() - 5, 0), trend.size());

                Map<String, Object> data = new HashMap<>();
                data.put("name", name);
                data.put("value", Math.round(current * 100.0) / 100.0);
                data.put("change", change);
                data.put("trend", trend);

                indexList.add(data);
            }

            // 🔹 공포·탐욕 지수 계산
            double sp500 = getLatestClose(restTemplate, mapper, "^GSPC");
            double vix = getLatestClose(restTemplate, mapper, "^VIX");
            double treasury = getLatestClose(restTemplate, mapper, "^TNX");

            double fearGreed = 50.0;
            fearGreed += (sp500 > 5000 ? 5 : -5);
            fearGreed -= (vix > 20 ? 10 : -5);
            fearGreed -= (treasury > 4.0 ? 5 : 0);

            fearGreed = Math.min(100, Math.max(0, fearGreed));

            String sentiment;
            if (fearGreed <= 25) sentiment = "극단적 공포 (Extreme Fear)";
            else if (fearGreed <= 45) sentiment = "공포 (Fear)";
            else if (fearGreed <= 55) sentiment = "중립 (Neutral)";
            else if (fearGreed <= 75) sentiment = "탐욕 (Greed)";
            else sentiment = "극단적 탐욕 (Extreme Greed)";

            // 🔹 모델 전달
            model.addAttribute("indices", indexList);
            model.addAttribute("fearGreed", fearGreed);
            model.addAttribute("sentiment", sentiment);
            model.addAttribute("updated", new Date().toString());

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "데이터를 불러오지 못했습니다: " + e.getMessage());
        }

        return "market-dashboard";
    }
	
	private double getLatestClose(RestTemplate restTemplate, ObjectMapper mapper, String symbol) throws Exception {
        String url = String.format("https://query1.finance.yahoo.com/v8/finance/chart/%s?interval=1d&range=5d", symbol);
        JsonNode root = mapper.readTree(restTemplate.getForEntity(url, String.class).getBody());
        JsonNode closes = root.path("chart").path("result").get(0)
                .path("indicators").path("quote").get(0).path("close");
        List<Double> list = new ArrayList<>();
        for (JsonNode n : closes) if (!n.isNull()) list.add(n.asDouble());
        return list.get(list.size() - 1);
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