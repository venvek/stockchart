package com.jucha.stockchart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResultDto {
    private String ticker;
    private String companyName;

    public SearchResultDto(String ticker, String companyName) {
        this.ticker = ticker;
        this.companyName = companyName;
    }
}