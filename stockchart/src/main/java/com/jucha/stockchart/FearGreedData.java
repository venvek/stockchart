package com.jucha.stockchart;

import java.time.LocalDate;

public class FearGreedData {
    private LocalDate date;
    private double indexValue;

    public FearGreedData(LocalDate date, double indexValue) {
        this.date = date;
        this.indexValue = indexValue;
    }

    public LocalDate getDate() { return date; }
    public double getIndexValue() { return indexValue; }
}