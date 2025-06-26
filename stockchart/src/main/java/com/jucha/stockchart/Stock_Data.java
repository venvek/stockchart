package com.jucha.stockchart;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "stock_data")
public class Stock_Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "open", precision = 15, scale = 2)
    private BigDecimal open;

    @Column(name = "high", precision = 15, scale = 2)
    private BigDecimal high;

    @Column(name = "low", precision = 15, scale = 2)
    private BigDecimal low;

    @Column(name = "close", precision = 15, scale = 2)
    private BigDecimal close;

    @Column(name = "volume")
    private Long volume;

    @Column(name = "dividends", precision = 15, scale = 2)
    private BigDecimal dividends;

    @Column(name = "stock_splits", precision = 15, scale = 2)
    private BigDecimal stockSplits;

    @ManyToOne
    @JoinColumn(name = "ticker", referencedColumnName = "ticker")
    private Company company;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company companys;

    public Stock_Data() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Company getCompany() {
        return company ;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    
}

