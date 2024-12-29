package org.example.springstudy;

import org.example.springstudy.threadLocal.CustomRequestContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public FilterRegistrationBean<CustomRequestContextFilter> threadLocalTest() {
		FilterRegistrationBean<CustomRequestContextFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new CustomRequestContextFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(1);

		return registrationBean;

	}

}
