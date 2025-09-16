package com.jucha.stockchart;

import java.util.Map;

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
    
    private Long getUserIdFromPrincipal(OAuth2User principal) {
        if (principal == null) return null;

        Object idObj = principal.getAttribute("id");
        return (idObj != null) ? Long.valueOf(idObj.toString()) : null;
    }
}
