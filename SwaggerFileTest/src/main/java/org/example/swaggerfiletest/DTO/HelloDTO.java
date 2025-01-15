package org.example.swaggerfiletest.DTO;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class HelloDTO {
	private String hello;
	private MultipartFile image;
}
