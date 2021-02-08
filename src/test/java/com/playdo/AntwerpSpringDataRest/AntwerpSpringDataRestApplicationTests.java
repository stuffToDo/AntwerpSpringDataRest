package com.playdo.AntwerpSpringDataRest;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AntwerpSpringDataRestApplicationTests {

	private static final String APPLICATION_JSON_HAL = "application/hal+json";

	@Autowired
	private MockMvc mock;

	@Test
	public void contextLoads() {
	}

	@Test
	public void addPortfolios() throws Exception {
		mock.perform(post("/portfolios/")
				.content("{\"stocks\":[\"GOOG\",\"AAPL\",\"TSLA\"]}")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(APPLICATION_JSON_HAL))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_HAL));
	}

	@Test
	public void customGetPortfolios() throws Exception {
		mock.perform(get("/portfolios/")
				.accept(APPLICATION_JSON_HAL))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_HAL))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].stocks", hasItem("goog-beta")))
				.andExpect(jsonPath("$[0].stocks", hasItem("aapl-beta")))
				.andExpect(jsonPath("$[0].stocks", hasItem("tsla-beta")));
	}

	@Test
	public void putsAreNotAllowed() throws Exception {
		mock.perform(put("/portfolios/")
				.content("{\"stocks\":[\"GOOG\",\"AAPL\",\"TSLA\"]}")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}
}
