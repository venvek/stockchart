package com.jucha.stockchart;

import java.util.HashMap;
import java.util.List;
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

    @GetMapping("/search")
    public Map<String, List<String>> search(@RequestParam("q") String query,
                                            @AuthenticationPrincipal(expression = "attributes['id']") String userId) {
        Long uid = (userId != null) ? Long.valueOf(userId) : null;

        Map<String, List<String>> result = new HashMap<>();
        result.put("favorites", searchService.getFavorites(uid, query));
        result.put("recent", searchService.getRecentSearches(uid, query));
        result.put("results", searchService.searchTickers(query));

        return result;
    }
    
    private Long getUserIdFromPrincipal(OAuth2User principal) {
        if (principal == null) return null;

        Object idObj = principal.getAttribute("id");
        return (idObj != null) ? Long.valueOf(idObj.toString()) : null;
    }
}
