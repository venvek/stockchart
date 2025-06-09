package com.jucha.stockchart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CompanyService {
	private CompanyRepository companyrepository;
	private StockDataRepo stockdatarepositoty;
	
	public CompanyService(CompanyRepository companyrepository) {
		this.companyrepository = companyrepository;
	}
	
	public List<String> getSuggetsions(String query) {
		return companyrepository.findTickersByQuery(query);
	}
	
	public List<Company> getAllCompanies() {
        return companyrepository.findAll();
    }
	
	public List<CompanyHeatmapDTO> getCompaniesWithPriceChange() {
	    List<Company> companies = companyrepository.findAll();
	    List<CompanyHeatmapDTO> result = new ArrayList<>();

	    for (Company company : companies) {
	        BigDecimal previousClose = company.getPreviousClose();
	        BigDecimal currentPrice = stockdatarepositoty.findLatestCloseByTicker(company.getTicker());

	        if (previousClose != null && currentPrice != null) {
	            double changePercent = currentPrice
	                .subtract(previousClose)
	                .divide(previousClose, 4, RoundingMode.HALF_UP)
	                .multiply(BigDecimal.valueOf(100))
	                .doubleValue();

	            CompanyHeatmapDTO dto = new CompanyHeatmapDTO();
	            dto.setName(company.getName());
	            dto.setTicker(company.getTicker());
	            dto.setPreviousClose(previousClose);
	            dto.setCurrentPrice(currentPrice);
	            dto.setPriceChangePercent(changePercent);

	            result.add(dto);
	        }
	    }

	    return result;
	}
}
