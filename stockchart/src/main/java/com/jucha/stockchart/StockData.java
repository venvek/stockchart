package com.jucha.stockchart;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Long volume;
    private Float dividends;
    private Float stockSplits;
    private String ticker;

    // 기본 생성자
    public StockData() {}

    // 생성자
    public StockData(Date date, Float open, Float high, Float low, Float close, Long volume, Float dividends, Float stockSplits, String ticker) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.dividends = dividends;
        this.stockSplits = stockSplits;
        this.ticker = ticker;
    }

    // Getter 및 Setter 메서드
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getOpen() {
        return open;
    }

    public void setOpen(Float open) {
        this.open = open;
    }

    public Float getHigh() {
        return high;
    }

    public void setHigh(Float high) {
        this.high = high;
    }

    public Float getLow() {
        return low;
    }

    public void setLow(Float low) {
        this.low = low;
    }

    public Float getClose() {
        return close;
    }

    public void setClose(Float close) {
        this.close = close;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Float getDividends() {
        return dividends;
    }

    public void setDividends(Float dividends) {
        this.dividends = dividends;
    }

    public Float getStockSplits() {
        return stockSplits;
    }

    public void setStockSplits(Float stockSplits) {
        this.stockSplits = stockSplits;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
