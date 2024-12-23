package org.example.springstudy.MDC;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class MDCTest {

	private static final Logger logger = LoggerFactory.getLogger(MDCTest.class);

	public static void main(String[] args) {
		// 스레드 풀 생성
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		// 서로 다른 스레드에서 MDC 값을 설정하고 로그 출력
		for (int i = 1; i <= 3; i++) {
			int threadId = i;
			executorService.submit(() -> {
				MDC.put("requestId", "request-" + threadId); // 스레드별로 고유한 값 설정
				MDC.put("userId", "user-" + threadId);       // 사용자 ID 설정

				try {
					logger.info("Processing request in thread {}", threadId);
				} finally {
					MDC.clear(); // MDC 데이터 정리
				}
			});
		}

		executorService.shutdown();
	}

}
