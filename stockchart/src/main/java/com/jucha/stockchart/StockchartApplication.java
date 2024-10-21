package com.jucha.stockchart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.jucha.stockchart")  // Adjust the package name to where your repositories are
@EntityScan("com.jucha.stockchart.Entity") 
public class StockchartApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockchartApplication.class, args);
	}

}
