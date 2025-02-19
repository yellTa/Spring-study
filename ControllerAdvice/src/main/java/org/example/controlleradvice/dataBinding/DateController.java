package org.example.controlleradvice.dataBinding;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/date")
public class DateController {

	@GetMapping
	public String getFormattedDate(@RequestParam CustomDate customDate) {
		return "Formatted Date: " + customDate;
	}

}
