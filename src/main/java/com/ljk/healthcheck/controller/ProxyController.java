package com.ljk.healthcheck.controller;

import static com.ljk.healthcheck.controller.HealthCheckController.*;

import java.util.Set;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/proxy")
@RequiredArgsConstructor
public class ProxyController {

	private final WebClient webClient;

	@RequestMapping("/**")
	public Mono<ResponseEntity<DataBuffer>> forwardRequest(ServerHttpRequest request) {

		String url;

		Set<String> upServers = AVAILABLE_SERVERS.get("up");

		if (!upServers.isEmpty()) {
			url = upServers.iterator().next();
		} else {
			return Mono.empty();
		}

		System.out.println(url + request.getPath().toString().substring(6));

		return webClient.method(request.getMethod())
				   .uri(url + request.getPath().toString().substring(6))
				   .headers(headers -> headers.addAll(request.getHeaders()))
				   .body(request.getBody(), DataBuffer.class)
				   .exchangeToMono(clientResponse -> {
					   HttpHeaders responseHeaders = new HttpHeaders();

					   System.out.println("headers:: " + clientResponse.headers().asHttpHeaders());
					   clientResponse.headers().asHttpHeaders().forEach(responseHeaders::addAll);

					   return clientResponse.bodyToMono(DataBuffer.class)
								  .map(body -> ResponseEntity.status(clientResponse.statusCode())
												   .headers(responseHeaders)
												   .body(body));
				   });
	}
}

