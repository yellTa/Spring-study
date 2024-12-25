package org.example.springstudy.threadLocal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("threadLocal")
@Slf4j
public class ThreadLocalController {
	@GetMapping
	public String threadLocalRequest() {
		log.info("ThreadLocalController Hello!");
		return "hello!";
	}

}
