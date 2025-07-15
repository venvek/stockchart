package com.jucha.stockchart;

import java.math.BigDecimal;

public class CompanyHeatmapDTO {

	private String ticker;
    private String name;
    private BigDecimal latestPrice;
    private BigDecimal previousClose;
    private BigDecimal changeRate;
    
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getLatestPrice() {
		return latestPrice;
	}
	public void setLatestPrice(BigDecimal latestPrice) {
		this.latestPrice = latestPrice;
	}
	public BigDecimal getPreviousClose() {
		return previousClose;
	}
	public void setPreviousClose(BigDecimal previousClose) {
		this.previousClose = previousClose;
	}
	public BigDecimal getChangeRate() {
		return changeRate;
	}
	public void setChangeRate(BigDecimal changeRate) {
		this.changeRate = changeRate;
	}

    
}
