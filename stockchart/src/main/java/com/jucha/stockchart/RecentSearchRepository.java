package com.jucha.stockchart;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {
    
    List<RecentSearch> findTop5ByUserIdOrderBySearchedAtDesc(Long userId);
    
    boolean existsByUserIdAndTicker(Long userId, String ticker);

    void deleteByUserIdAndTicker(Long userId, String ticker);
    
    List<RecentSearch> findTop5ByUserIdAndTickerContainingIgnoreCaseOrderBySearchedAtDesc(Long userId, String ticker);
    
}

