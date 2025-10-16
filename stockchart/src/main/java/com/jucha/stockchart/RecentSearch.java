package com.jucha.stockchart;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recent_searches")
public class RecentSearch {

 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long userId;

	    private String ticker;

	    private LocalDateTime searchedAt = LocalDateTime.now();

	    // ✅ 기본 생성자
	    public RecentSearch() {}

	    public RecentSearch(Long userId, String ticker) {
	        this.userId = userId;
	        this.ticker = ticker;
	        this.searchedAt = LocalDateTime.now();
	    }
	    
	    public Long getId() {
	        return id;
	    }

	    public Long getUserId() {
	        return userId;
	    }

	    public void setUserId(Long userId) {
	        this.userId = userId;
	    }

	    public String getTicker() {
	        return ticker;
	    }

	    public void setTicker(String ticker) {
	        this.ticker = ticker;
	    }

	    public LocalDateTime getSearchedAt() {
	        return searchedAt;
	    }

	    public void setSearchedAt(LocalDateTime searchedAt) {
	        this.searchedAt = searchedAt;
	    }
	    
}