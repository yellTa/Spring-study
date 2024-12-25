package org.example.springstudy.threadLocal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(controllers = ThreadLocalController.class)
@Import(CustomRequestContextFilter.class)
@AutoConfigureMockMvc
class RequestContextFilterTest {

	private final CustomRequestContextFilter customRequestContextFilter = new CustomRequestContextFilter();

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ThreadLocalController())
									  .addFilters(customRequestContextFilter)
									  .build();
	}

	@Test
	@DisplayName("MDC with threadLocal")
	public void testRequestContextFilter() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/threadLocal")
											  .header("Authorization", "user123"))
			   .andExpect(status().isOk())
			   .andDo(request -> {
				   String userId = CustomRequestContext.getUserId();
				   assertEquals("user123", userId);
			   });
		CustomRequestContext.clear();

		assertNull(CustomRequestContext.getUserId());
	}
}