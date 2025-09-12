package com.jucha.stockchart;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);

    List<Favorite> findByUserIdAndTickerContainingIgnoreCase(Long userId, String ticker);
}