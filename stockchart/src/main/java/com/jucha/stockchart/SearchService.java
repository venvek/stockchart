package com.jucha.stockchart;

import java.util.List;
import java.util.stream.Collectors;

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
        return favoriteRepo.findByUserIdAndTickerContainingIgnoreCase(userId, query)
                .stream()
                .map(Favorite::getTicker)  // ✅ ticker 값만 꺼내기
                .collect(Collectors.toList());
    }

    public List<String> getRecentSearches(Long userId, String query) {
        if (userId == null) return List.of();
        return recentRepo.findTop5ByUserIdAndTickerContainingIgnoreCaseOrderByViewedAtDesc(userId, query)
                .stream()
                .map(RecentSearch::getTicker) // ✅ ticker 값만 꺼내기
                .collect(Collectors.toList());
    }

    public List<SearchResultDto> searchTickers(String query) {
        List<Stock_Data> stockList =
            stockRepo.findTop10ByTickerContainingIgnoreCaseOrCompany_NameContainingIgnoreCase(query, query);
        return stockList.stream()
            .map(s -> new SearchResultDto(
                s.getTicker(),
                (s.getCompany() != null ? s.getCompany().getName() : "")
            ))
            .collect(Collectors.toList());
    }
}