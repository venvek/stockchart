package com.jucha.stockchart;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "indicators")
public class Indicators {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "ticker", referencedColumnName = "ticker", nullable = false)
	private Company company;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@Column(name = "rsi")
	private BigDecimal rsi;

	@Column(name = "cci")
	private BigDecimal cci;

	@Column(name = "rs")
	private BigDecimal rs;

	@Column(name = "obv")
	private BigDecimal obv;

	@Column(name = "macd")
	private BigDecimal macd;

	@Column(name = "stochastic")
	private BigDecimal stochastic;

	@Column(name = "ma")
	private BigDecimal ma;

	@Column(name = "bb_upper")
	private BigDecimal bbUpper;

	@Column(name = "bb_lower")
	private BigDecimal bbLower;

	// Other fields...

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
