package com.ljk.healthcheck.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
public class HealthCheckController {

	public static final Map<String, Set<String>> AVAILABLE_SERVERS = Map.of("up", new HashSet<>(),
		"down", new HashSet<>());

	@PostMapping("/up")
	public void up(ServerHttpRequest request) {
		String url =
			request.getURI().getScheme() + "://" + request.getURI().getHost() + ":" + request.getURI().getPort();
		AVAILABLE_SERVERS.get("up").add(url);
		AVAILABLE_SERVERS.get("down").remove(url);
	}

	@PostMapping("/down")
	public void down(ServerHttpRequest request) {
		String url =
			request.getURI().getScheme() + "://" + request.getURI().getHost() + ":" + request.getURI().getPort();
		AVAILABLE_SERVERS.get("up").remove(url);
		AVAILABLE_SERVERS.get("down").add(url);
	}

}
