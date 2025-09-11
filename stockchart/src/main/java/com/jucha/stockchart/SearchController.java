package com.jucha.stockchart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public Map<String, Object> search(
            @RequestParam("q") String query,
            @AuthenticationPrincipal OAuth2User principal) {

        Long userId = getUserIdFromPrincipal(principal);

        return Map.of(
            "favorites", searchService.getFavorites(userId, query),
            "recent", searchService.getRecentSearches(userId, query),
            "results", searchService.searchTickers(query)
        );
    }
}
