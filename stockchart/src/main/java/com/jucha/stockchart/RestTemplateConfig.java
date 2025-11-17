package com.jucha.stockchart;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();

        // Yahoo는 User-Agent 없으면 429 발생
        restTemplate.setInterceptors(List.of((request, body, execution) -> {
            request.getHeaders().add("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                            + "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
            return execution.execute(request, body);
        }));

        return restTemplate;
    }
}
