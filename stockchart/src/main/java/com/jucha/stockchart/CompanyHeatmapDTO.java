package com.jucha.stockchart;

import java.math.BigDecimal;

public class CompanyHeatmapDTO {

	private String name;
    private String ticker;
    private BigDecimal previousClose;
    private BigDecimal currentPrice;
    private Double priceChangePercent;

    // 생성자
    public CompanyHeatmapDTO(String name, String ticker, BigDecimal previousClose, BigDecimal currentPrice, Double priceChangePercent) {
        this.name = name;
        this.ticker = ticker;
        this.previousClose = previousClose;
        this.currentPrice = currentPrice;
        this.priceChangePercent = priceChangePercent;
    }
    
}
