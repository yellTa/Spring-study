package org.example.springstudy.MDC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;

public class LogBackProgrammingConfig {
	public static void main(String[] args) {
		LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();
		// Console Appender 생성
		ConsoleAppender consoleAppender = new ConsoleAppender();
		consoleAppender.setContext(context);

		// 패턴 레이아웃 설정
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(context);
		//날짜 + 시간 스레드 이름 출력 / 로그를 호출한 클래스 이름/ MDC에서 지정한 키 value값 /tlfwp fhrm aptpwl
		encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - [requestId=%X{requestId}] %msg%n");
		encoder.start();

		// Appender와 Encoder 연결
		consoleAppender.setEncoder(encoder);
		consoleAppender.start();

		// Root Logger 설정
		ch.qos.logback.classic.Logger rootLogger = context.getLogger("ROOT");
		rootLogger.addAppender(consoleAppender);

		// 테스트 로그
		Logger logger = LoggerFactory.getLogger(LogBackProgrammingConfig.class);
		logger.info("This is a test log with MDC.");
	}
}
