package com.jucha.stockchart;

import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeatMapData {
    private String ticker;
    private String name;
    private BigDecimal marketCap;
    private BigDecimal previousClose;
    private BigDecimal open;
    private BigDecimal dayLow;
    private BigDecimal dayHigh;
    private BigInteger volume;
    private double changePercent; // 주가 변동률

    public HeatMapData(String ticker, String name, BigDecimal marketCap, BigDecimal previousClose,
                       BigDecimal open, BigDecimal dayLow, BigDecimal dayHigh, BigInteger volume, double changePercent) {
        this.ticker = ticker;
        this.name = name;
        this.marketCap = marketCap;
        this.previousClose = previousClose;
        this.open = open;
        this.dayLow = dayLow;
        this.dayHigh = dayHigh;
        this.volume = volume;
        this.changePercent = changePercent;
    }

	@Override
	public String toString() {
		return "HeatMapData [ticker=" + ticker + ", name=" + name + ", marketCap=" + marketCap + ", previousClose="
				+ previousClose + ", open=" + open + ", dayLow=" + dayLow + ", dayHigh=" + dayHigh + ", volume="
				+ volume + ", changePercent=" + changePercent + "]";
	}
    
    
    
    
}