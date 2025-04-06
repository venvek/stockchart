package com.jucha.stockchart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockPeriodData {
	
    private String ticker;
    private String period;  
    private Double close;   
    
}
