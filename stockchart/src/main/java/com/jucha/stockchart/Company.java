package com.jucha.stockchart;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;


@Getter
@Entity
@Table(name = "Company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(nullable = false, unique = true)
    private String ticker; 

    @Column(name = "sector", length = 100)
    private String sector;

    @Column(name = "industry", length = 100)
    private String industry;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "fax", length = 50)
    private String fax;

    @Column(name = "website", length = 255)
    private String website;

    @Column(name = "longBusinessSummary", columnDefinition = "TEXT")
    private String longBusinessSummary;

    @Column(name = "fullTimeEmployees")
    private Integer fullTimeEmployees;

    @Column(name = "marketCap")
    private BigInteger marketCap;

    @Column(name = "previousClose", precision = 15, scale = 2)
    private BigDecimal previousClose;

    @Column(name = "open", precision = 15, scale = 2)
    private BigDecimal open;

    @Column(name = "dayLow", precision = 15, scale = 2)
    private BigDecimal dayLow;

    @Column(name = "dayHigh", precision = 15, scale = 2)
    private BigDecimal dayHigh;

    @Column(name = "dividendRate", precision = 15, scale = 2)
    private BigDecimal dividendRate;

    @Column(name = "dividendYield", precision = 5, scale = 4)
    private BigDecimal dividendYield;

    @Column(name = "beta", precision = 5, scale = 4)
    private BigDecimal beta;

    @Column(name = "volume")
    private BigInteger volume;

    @Column(name = "sharesOutstanding")
    private BigInteger sharesOutstanding;

    @Column(name = "netIncomeToCommon")
    private BigInteger netIncomeToCommon;

    @Column(name = "totalRevenue")
    private BigInteger totalRevenue;

    @Column(name = "operatingCashflow")
    private BigInteger operatingCashflow;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "companys", fetch = FetchType.LAZY)
    private List<Stock_Data> stockDataList;
  
}
