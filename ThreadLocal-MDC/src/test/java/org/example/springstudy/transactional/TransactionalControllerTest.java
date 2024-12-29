package org.example.springstudy.transactional;

import java.time.LocalDate;

import org.example.springstudy.transactional.DTO.Tasks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionalControllerTest {

	@Autowired
	private TransactionalController controller;

	@Test
	@DisplayName("Transactional Debugging Test")
	public void transactionalDebuggingTest() {
		Tasks userTask = new Tasks();
		userTask.setTitle("hello");
		userTask.setDate(LocalDate.now());
		userTask.setOwner("YE JI RYU");
		userTask.setPriority(1);
		userTask.setProgress("todo");

		String result = controller.diggingTransactional(userTask);
		System.out.println(result);

	}
}