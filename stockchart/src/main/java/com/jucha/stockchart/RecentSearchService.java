package com.jucha.stockchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class RecentSearchService {

    @Autowired
    private RecentSearchRepository recentRepo;

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
    
    @Transactional
    public void saveRecentSearch(Long userId, String ticker) {
        // 중복 제거 후 저장
        if (recentRepo.existsByUserIdAndTicker(userId, ticker)) {
            recentRepo.deleteByUserIdAndTicker(userId, ticker);
        }
        recentRepo.save(new RecentSearch(userId, ticker)); // ✅ 여기서 생성자 사용
    }

    public List<String> getRecentTickers(Long userId) {
        return recentRepo.findTop5ByUserIdOrderBySearchedAtDesc(userId)
                .stream()
                .map(RecentSearch::getTicker)
                .toList();
    }
}
