package com.jucha.stockchart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
	private CompanyRepository companyrepository;
	private StockDataRepo stockDataRepository;
	
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
	        List<BigDecimal> currentPrices = companyrepository.findLatestCloseByTicker(
	            company.getTicker(), PageRequest.of(0, 1)
	        );

	        if (!currentPrices.isEmpty() && previousClose != null && previousClose.compareTo(BigDecimal.ZERO) != 0) {
	            BigDecimal currentPrice = currentPrices.get(0);
	            BigDecimal change = currentPrice.subtract(previousClose);
	            BigDecimal percent = change.divide(previousClose, 4, RoundingMode.HALF_UP)
	                                       .multiply(BigDecimal.valueOf(100));

	            result.add(new CompanyHeatmapDTO(
	                company.getName(),
	                company.getTicker(),
	                previousClose,
	                currentPrice,
	                percent.doubleValue()
	            ));
	        }
	    }

	    return result;
	}
}
