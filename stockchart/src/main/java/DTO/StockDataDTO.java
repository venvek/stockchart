package DTO;

import java.math.BigDecimal;
import java.util.Date;

public class StockDataDTO {
    private Date date;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private Long volume;
    private BigDecimal dividends;
    private BigDecimal stockSplits;
    private String ticker;

    // Constructors, Getters, and Setters

    public StockDataDTO() {
    }

    public StockDataDTO(Date date, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, Long volume, BigDecimal dividends, BigDecimal stockSplits, String ticker) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public BigDecimal getDividends() {
        return dividends;
    }

    public void setDividends(BigDecimal dividends) {
        this.dividends = dividends;
    }

    public BigDecimal getStockSplits() {
        return stockSplits;
    }

    public void setStockSplits(BigDecimal stockSplits) {
        this.stockSplits = stockSplits;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
