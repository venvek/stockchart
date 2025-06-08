package com.jucha.stockchart;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CompanyService {
	private CompanyRepository companyrepository;
	
	public CompanyService(CompanyRepository companyrepository) {
		this.companyrepository = companyrepository;
	}
	
	public List<String> getSuggetsions(String query) {
		return companyrepository.findTickersByQuery(query);
	}
	
	public List<Company> getAllCompanies() {
        return companyrepository.findAll();
    }
	
}
