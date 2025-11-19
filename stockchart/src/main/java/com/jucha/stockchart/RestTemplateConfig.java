package com.jucha.stockchart;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate() {
	    var factory = new SimpleClientHttpRequestFactory();
	    factory.setConnectTimeout(5000);
	    factory.setReadTimeout(5000);

	    RestTemplate template = new RestTemplate(factory);

	    template.setInterceptors(List.of((request, body, execution) -> {
	        request.getHeaders().add("User-Agent",
	                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
	                        + "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
	        return execution.execute(request, body);
	    }));

	    return template;
	}
}