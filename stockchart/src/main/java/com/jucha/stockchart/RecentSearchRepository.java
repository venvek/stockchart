package com.jucha.stockchart;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {
    List<RecentSearch> findTop5ByUserIdOrderByViewedAtDesc(Long userId);

    List<RecentSearch> findTop5ByUserIdAndTickerContainingIgnoreCaseOrderByViewedAtDesc(
            Long userId, String ticker);
    
    List<RecentSearch> findTop5ByUserIdOrderBySearchedAtDesc(Long userId);
}

