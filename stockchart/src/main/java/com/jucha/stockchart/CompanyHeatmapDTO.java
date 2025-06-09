package com.jucha.stockchart;

import java.math.BigDecimal;

public class CompanyHeatmapDTO {

	private String name;
    private String ticker;
    private BigDecimal previousClose;
    private BigDecimal currentPrice;
    private Double priceChangePercent;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public BigDecimal getPreviousClose() {
		return previousClose;
	}
	public void setPreviousClose(BigDecimal previousClose) {
		this.previousClose = previousClose;
	}
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	public Double getPriceChangePercent() {
		return priceChangePercent;
	}
	public void setPriceChangePercent(Double priceChangePercent) {
		this.priceChangePercent = priceChangePercent;
	}
	@Override
	public String toString() {
		return "CompanyHeatmapDTO [name=" + name + ", ticker=" + ticker + ", previousClose=" + previousClose
				+ ", currentPrice=" + currentPrice + ", priceChangePercent=" + priceChangePercent + "]";
	}
    
    
}
