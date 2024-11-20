package com.jucha.stockchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuggestController {
	
	@Autowired
	private CompanyService companyService;

    @GetMapping("/suggestions")
    public List<String> getSuggestions(@RequestParam String ticker) {
        // 입력된 검색어로 필터링된 결과 반환
        return companyService.getSuggetsions(ticker);
    }
}
