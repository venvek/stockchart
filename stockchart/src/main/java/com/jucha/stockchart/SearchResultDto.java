package com.jucha.stockchart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchResultDto {
    private String ticker;
    private String companyName;


}