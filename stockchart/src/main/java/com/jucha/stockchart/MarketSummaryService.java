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
	        "KOSPI", "^KS11"
	    );

	    private static final String API_URL =
	            "https://query1.finance.yahoo.com/v8/finance/chart/%s?interval=1d&range=7d";

	    private final RestTemplate restTemplate;
	    private final ObjectMapper objectMapper = new ObjectMapper();

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
	                indexInfo.put("value", String.format("%.2f", last));
	                indexInfo.put("change", String.format("%.2f", changePercent));
	                indexInfo.put("trend", trend);

	                resultList.add(indexInfo);
	            } catch (Exception e) {
	                System.out.println("Error fetching: " + name + " / " + e.getMessage());
	            }
	        }
	        return resultList;
	    }
	}