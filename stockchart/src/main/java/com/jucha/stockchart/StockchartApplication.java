package com.jucha.stockchart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.jucha.stockchart")
public class StockchartApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockchartApplication.class, args);
	}

}
