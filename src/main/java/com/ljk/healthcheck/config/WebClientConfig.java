package com.ljk.healthcheck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientConfig {

	@Bean
	public WebClient webClient() {
		return WebClient.create();
	}
}
