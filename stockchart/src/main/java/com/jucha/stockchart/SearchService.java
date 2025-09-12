package com.jucha.stockchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    private FavoriteRepository favoriteRepo;

    @Autowired
    private RecentSearchRepository recentRepo;

    @Autowired
    private StockDataRepo stockRepo; // ticker 검색용

    public List<String> getFavorites(Long userId, String query) {
        if (userId == null) return List.of();
        return favoriteRepo.findByUserIdAndTickerContainingIgnoreCase(userId, query);
    }

    public List<String> getRecentSearches(Long userId, String query) {
        if (userId == null) return List.of();
        return recentRepo.findTop5ByUserIdAndTickerContainingIgnoreCaseOrderByViewedAtDesc(userId, query);
    }

    public List<String> searchTickers(String query) {
        return stockRepo.findTop10ByTickerContainingIgnoreCaseOrNameContainingIgnoreCase(query, query);
    }
}