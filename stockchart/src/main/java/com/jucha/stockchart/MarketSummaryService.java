package com.jucha.stockchart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MarketSummaryService {

	private static final Map<String, String> INDICES = Map.of(
	        "S&P 500", "^GSPC",
	        "NASDAQ", "^IXIC",
	        "Dow Jones", "^DJI",
	        "KOSPI", "^KS11",
	        "USD" , "DALLER"
	    );

	    private static final String API_URL =
	            "https://query1.finance.yahoo.com/v8/finance/chart/%s?interval=1d&range=7d";

	    private final RestTemplate restTemplate;
	    private final ObjectMapper objectMapper = new ObjectMapper();

	    public Map<String, Object> getMockMarketSummary() {
	        Map<String, Object> data = new HashMap<>();

	        data.put("S&P 500", Map.of(
	                "price", 5021.15,
	                "change", +12.45,
	                "percent", "+0.25%"
	        ));

	        data.put("NASDAQ", Map.of(
	                "price", 15987.42,
	                "change", -25.11,
	                "percent", "-0.16%"
	        ));

	        data.put("Dow Jones", Map.of(
	                "price", 38250.77,
	                "change", +85.22,
	                "percent", "+0.22%"
	        ));

	        data.put("KOSPI", Map.of(
	                "price", 2601.28,
	                "change", -12.15,
	                "percent", "-0.46%"
	        ));
	        
	        data.put("USD", Map.of(
	                "price", 1473,
	                "change", +6.1,
	                "percent", "+0.42%"
	        ));

	        // 공포·탐욕지수(VIX 대신 Fear-Greed Index)
	        data.put("FearGreed", Map.of(
	                "score", 62,
	                "status", "Greed"
	        ));

	        return data;
	    }

	    private Map<String, Object> createIndex(
	            String name,
	            double value,
	            double change,
	            List<Double> trend
	    ) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("name", name);
	        map.put("value", String.format("%.2f", value));
	        map.put("change", String.format("%.2f", change));
	        map.put("trend", trend);
	        return map;
	    }
	    
	    @Autowired
	    public MarketSummaryService(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }

	    public List<Map<String, Object>> fetchMarketIndices() {
	        List<Map<String, Object>> resultList = new ArrayList<>();
	        

	        for (var entry : INDICES.entrySet()) {
	            String name = entry.getKey();
	            String symbol = entry.getValue();
	            String url = String.format(API_URL, symbol);

	            try {
	                String response = restTemplate.getForObject(url, String.class);
	                JsonNode root = objectMapper.readTree(response);

	                JsonNode result = root.path("chart").path("result").get(0);
	                if (result == null) continue;

	                JsonNode closes = result.path("indicators").path("quote").get(0).path("close");

	                double last = closes.get(closes.size() - 1).asDouble();
	                double prev = closes.get(closes.size() - 2).asDouble();
	                double changePercent = ((last - prev) / prev) * 100;

	                List<Double> trend = new ArrayList<>();
	                closes.forEach(c -> {
	                    if (!c.isNull()) trend.add(c.asDouble());
	                });

	                Map<String, Object> indexInfo = new HashMap<>();
	                indexInfo.put("name", name);
	                indexInfo.put("value", last);            // 🔥 double
	                indexInfo.put("change", changePercent);  // 🔥 double
	                indexInfo.put("trend", trend);

	                resultList.add(indexInfo);

	            } catch (Exception e) {
	                System.out.println("Error fetching: " + name + " / " + e.getMessage());
	            }
	        }
	        return resultList;
	    }
	}