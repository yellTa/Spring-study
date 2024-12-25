package org.example.springstudy.threadLocal;

import org.springframework.stereotype.Component;

@Component
public class CustomRequestContext {
	private static final ThreadLocal<String> userContext = new ThreadLocal<>();

	public static String getUserId() {
		return userContext.get();
	}

	public static void setUserId(String userId) {
		userContext.set(userId);
	}

	public static void clear() {
		userContext.remove();
	}
}
