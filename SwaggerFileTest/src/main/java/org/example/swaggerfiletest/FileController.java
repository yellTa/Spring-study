package org.example.swaggerfiletest;

import org.example.swaggerfiletest.DTO.HelloDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileController {

	// @PostMapping(value = "/file/requestParam", consumes = {"multipart/form-data"})
	public ResponseEntity<?> fileUpload(@RequestParam("hello") String hello, @RequestParam("image") MultipartFile image) {
		HelloDTO helloDTO = new HelloDTO(hello, image);

		String message = String.format("Hello: %s, File Name: %s, File Size: %d bytes",
			helloDTO.getHello(),
			helloDTO.getImage().getOriginalFilename(),
			helloDTO.getImage().getSize());

		return ResponseEntity.ok(message);
	}

	@PostMapping(value = "/modelAttribute", consumes = "multipart/form-data")
	public ResponseEntity<?> fileUpload2(@ModelAttribute HelloDTO helloDTO) {

		String message = String.format("Hello: %s, File Name: %s, File Size: %d bytes",
			helloDTO.getHello(),
			helloDTO.getImage().getOriginalFilename(),
			helloDTO.getImage().getSize());

		return ResponseEntity.ok(message);
	}

}
