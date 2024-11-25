package com.ljk.healthcheck.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class DefaultController {

	@RequestMapping
	public Mono<ResponseEntity<Resource>> downloadFile() {
		String fileName = "test.png";
		// 파일 경로
		Path filePath = Paths.get("/Users/sakur35a/Desktop/", fileName);

		// 파일 리소스 생성
		Resource resource = new FileSystemResource(filePath);

		// 파일 존재 여부 확인
		if (!resource.exists()) {
			return Mono.error(new IllegalArgumentException("File not found: " + fileName));
		}

		// 응답 헤더 구성
		return Mono.just(
			ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(resource)
		);
	}

	@RequestMapping("/api")
	public ResponseEntity<Map<String, Object>> test() {
		return ResponseEntity.ok(Map.of("key", "hello world"));
	}
}
