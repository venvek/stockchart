package com.jucha.stockchart;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class FearGreedController {

    private final FearGreedService service;

    public FearGreedController(FearGreedService service) {
        this.service = service;
    }
    
    @GetMapping("/fear-greed")
    public String getFearGreedIndex(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // 데이터 가져오기
            double sp500 = getLatestClose(restTemplate, mapper, "^GSPC");
            double vix = getLatestClose(restTemplate, mapper, "^VIX");
            double treasury = getLatestClose(restTemplate, mapper, "^TNX"); // 10년물 금리

            // 간단한 가중치 기반 계산
            // (실제 CNN 방식은 아님, 시연용)
            double score = 50.0;
            score += (sp500 > 5000 ? 5 : -5);
            score -= (vix > 20 ? 10 : -5);
            score -= (treasury > 4.0 ? 5 : 0);

            // 0~100 사이로 정규화
            score = Math.min(100, Math.max(0, score));

            String sentiment;
            if (score <= 25) sentiment = "극단적 공포 (Extreme Fear)";
            else if (score <= 45) sentiment = "공포 (Fear)";
            else if (score <= 55) sentiment = "중립 (Neutral)";
            else if (score <= 75) sentiment = "탐욕 (Greed)";
            else sentiment = "극단적 탐욕 (Extreme Greed)";

            model.addAttribute("score", score);
            model.addAttribute("sentiment", sentiment);
            model.addAttribute("updated", new Date().toString());

        } catch (Exception e) {
            model.addAttribute("error", "지수를 불러오지 못했습니다: " + e.getMessage());
        }

        return "fear-greed";
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
	 * @GetMapping("/feargreed") public String fearGreedPage(Model model) {
	 * List<FearGreedData> data = service.getAll(); model.addAttribute("data",
	 * data); model.addAttribute("latest", service.getLatest()); return "feargreed";
	 * // -> templates/feargreed.html }
	 */

    @GetMapping("/api/feargreed")
    @ResponseBody
    public List<FearGreedData> getFearGreedData() {
        return service.getAll();
    }
}
