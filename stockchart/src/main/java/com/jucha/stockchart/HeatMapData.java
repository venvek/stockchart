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
	private String name;
    private String ticker;
    private String sector;
    private BigDecimal marketCap;
    private BigDecimal changePercent;

    public HeatMapData(String name, String ticker, String sector,
                      BigDecimal marketCap, BigDecimal changePercent) {
        this.name = name;
        this.ticker = ticker;
        this.sector = sector;
        this.marketCap = marketCap;
        this.changePercent = changePercent;
    }
    
    
    
    
}