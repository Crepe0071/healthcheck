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
		String url = getUrl(request);
		AVAILABLE_SERVERS.get("up").add(url);
		AVAILABLE_SERVERS.get("down").remove(url);
	}

	@PostMapping("/down")
	public void down(ServerHttpRequest request) {
		String url = getUrl(request);
		AVAILABLE_SERVERS.get("up").remove(url);
		AVAILABLE_SERVERS.get("down").add(url);
	}

	private String getUrl(ServerHttpRequest request) {
		int port;
		String scheme = request.getURI().getScheme();
		boolean isHttp = scheme.length() == 4;
		if (request.getURI().getPort() == -1) {
			port = isHttp ? 80 : 443;
		} else {
			port = request.getURI().getPort();
		}
		return scheme + "://" + request.getURI().getHost() + ":" + port;
	}

}
