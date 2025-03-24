package com.jucha.stockchart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AggregatedStockData {
	
    private String period;  // "2024-01", "2024"
    private Double avgClose;
    
}