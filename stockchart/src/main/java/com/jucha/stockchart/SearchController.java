package com.jucha.stockchart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private CompanyRepository companyRepo;
    
	
	 @GetMapping("/window") public Map<String, Object> search(@RequestParam("q")
	 String query,
	 
	 @AuthenticationPrincipal(expression = "attributes['id']") String userid) {
	 Long uid = (userid != null) ? Long.valueOf(userid) : null;
	 
	 Map<String, Object> result = new HashMap<>(); result.put("favorites",
	 searchService.getFavorites(uid, query)); result.put("recent",
	 //searchService.getRecentSearches(uid, query)); result.put("results",
	 searchService.searchTickers(query)); // ✅ DTO 리스트
	 
	 return result; }
	 
	 
    @GetMapping("/window2")
    public Map<String, Object> searchCompanies(@RequestParam("q") String query) {
        List<Company> results = companyRepo.findTop10ByTickerContainingIgnoreCaseOrNameContainingIgnoreCase(query, query);

        List<Map<String, String>> mappedResults = results.stream()
            .map(c -> {
                Map<String, String> map = new HashMap<>();
                map.put("ticker", c.getTicker());
                map.put("name", c.getName());
                return map;
            })
            .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("results", mappedResults);
        return response;
    }
    
    @GetMapping("/check")
    public Map<String, Object> checkTickerOrName(@RequestParam("tickerOrName") String query) {
        Map<String, Object> result = new HashMap<>();

        Optional<Company> companyOpt = companyRepo.findByTickerIgnoreCaseOrNameIgnoreCase(query, query);

        if (companyOpt.isPresent()) {
            result.put("exists", true);
            result.put("ticker", companyOpt.get().getTicker());
        } else {
            result.put("exists", false);
        }

        return result;
    }
    
    private Long getUserIdFromPrincipal(OAuth2User principal) {
        if (principal == null) return null;

        Object idObj = principal.getAttribute("id");
        return (idObj != null) ? Long.valueOf(idObj.toString()) : null;
    }
}
