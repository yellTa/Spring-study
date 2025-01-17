package org.example.controlleradvice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdviceController {

	@GetMapping("hello")
	public ResponseEntity<?> helloController(@RequestParam int id) {
		if (id == 1) {
			throw new HelloException("the ERROR");
		} else {
			return ResponseEntity.status(200)
								 .body("worked!");
		}
	}

}
