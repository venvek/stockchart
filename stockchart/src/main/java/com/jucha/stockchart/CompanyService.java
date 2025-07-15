package com.jucha.stockchart;

import java.io.Console;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
	private final CompanyRepository companyrepository;
	private final StockDataRepo stockDataRepository;  

    public CompanyService(CompanyRepository companyrepository, StockDataRepo stockDataRepository) {
        this.companyrepository = companyrepository;
        this.stockDataRepository = stockDataRepository; 
    }
	
	
	public List<String> getSuggetsions(String query) {
		return companyrepository.findTickersByQuery(query);
	}
	
	public List<Company> getAllCompanies() {
        return companyrepository.findAll();
    }

	public List<CompanyHeatmapDTO> getCompaniesWithPriceChange() {
	    List<Company> companies = companyrepository.findAll();
	    List<CompanyHeatmapDTO> heatmapList = new ArrayList<>();

	    for (Company company : companies) {
	        String ticker = company.getTicker();
	        String name = company.getName();
	        BigDecimal previousClose = company.getPreviousClose();

	        if (previousClose == null) continue;

	        List<BigDecimal> latestCloseList = stockDataRepository.findLatestCloseByTicker(ticker, PageRequest.of(0, 1));
	        if (latestCloseList.isEmpty()) continue;

	        BigDecimal latestClose = latestCloseList.get(0);
	        BigDecimal changeRate = latestClose.subtract(previousClose)
	                                           .divide(previousClose, 4, RoundingMode.HALF_UP)
	                                           .multiply(BigDecimal.valueOf(100));

	        CompanyHeatmapDTO dto = new CompanyHeatmapDTO();
	        dto.setTicker(ticker);
	        dto.setName(name);
	        dto.setLatestPrice(latestClose);
	        dto.setPreviousClose(previousClose);
	        dto.setChangeRate(changeRate);

	        heatmapList.add(dto);
	    }

	    return heatmapList;
	}
}

