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
	private Long id;

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
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public BigDecimal getRsi() {
		return rsi;
	}

	public void setRsi(BigDecimal rsi) {
		this.rsi = rsi;
	}

	public BigDecimal getCci() {
		return cci;
	}

	public void setCci(BigDecimal cci) {
		this.cci = cci;
	}

	public BigDecimal getRs() {
		return rs;
	}

	public void setRs(BigDecimal rs) {
		this.rs = rs;
	}

	public BigDecimal getObv() {
		return obv;
	}

	public void setObv(BigDecimal obv) {
		this.obv = obv;
	}

	public BigDecimal getMacd() {
		return macd;
	}

	public void setMacd(BigDecimal macd) {
		this.macd = macd;
	}

	public BigDecimal getStochastic() {
		return stochastic;
	}

	public void setStochastic(BigDecimal stochastic) {
		this.stochastic = stochastic;
	}

	public BigDecimal getMa() {
		return ma;
	}

	public void setMa(BigDecimal ma) {
		this.ma = ma;
	}

	public BigDecimal getBbUpper() {
		return bbUpper;
	}

	public void setBbUpper(BigDecimal bbUpper) {
		this.bbUpper = bbUpper;
	}

	public BigDecimal getBbLower() {
		return bbLower;
	}

	public void setBbLower(BigDecimal bbLower) {
		this.bbLower = bbLower;
	}

	
}
