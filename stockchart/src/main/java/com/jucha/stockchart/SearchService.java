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

    public List<String> getRecentTickers(Long userId) {
        return recentRepo.findTop5ByUserIdOrderBySearchedAtDesc(userId)
                .stream()
                .v(RecentSearch::getTicker)
                .toList();
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
    
    public void saveSearch(Long userId, String ticker) {
        if (userId == null || ticker == null || ticker.isBlank()) return;

        // 중복 제거
        recentRepo.deleteAll(
            recentRepo.findAll().stream()
                .filter(r -> r.getUserId().equals(userId) && r.getTicker().equalsIgnoreCase(ticker))
                .toList()
        );

        // 새 검색 저장
        RecentSearch search = new RecentSearch();
        search.setUserId(userId);
        search.setTicker(ticker);
        recentRepo.save(search);
    }
    
    
}