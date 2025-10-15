package com.jucha.stockchart;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recent")
public class RecentSearchController {

    @Autowired
    private RecentSearchRepository recentRepo;

    @PostMapping
    public void addRecentSearch(
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal OAuth2User principal) {

        Long userId = getUserIdFromPrincipal(principal);
        if (userId == null) return;

        String ticker = body.get("ticker");
        RecentSearch recent = new RecentSearch();
        recent.setUserId(userId);
        recent.setTicker(ticker);
        recentRepo.save(recent);
    }
    
    private Long getUserIdFromPrincipal(OAuth2User principal) {
        if (principal == null) return null;

        Object idObj = principal.getAttribute("id");
        return (idObj != null) ? Long.valueOf(idObj.toString()) : null;
    }
    
    @PostMapping("/record")
    public void recordSearch(
        @RequestParam("ticker") String ticker,
        @AuthenticationPrincipal(expression = "attributes['id']") String userId
    ) {
        Long uid = (userId != null) ? Long.valueOf(userId) : null;
        recentRepo.saveSearch(uid, ticker);
    }
}