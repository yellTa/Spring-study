package org.example.springstudy.threadLocal;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomRequestContextFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
		ServletException,
		IOException {

		String userId = request.getHeader("Authorization");
		MDC.put("requestId", userId);

		CustomRequestContext.setUserId(userId);
		MDC.put("userId", userId);

		filterChain.doFilter(request, response);

	}
}
