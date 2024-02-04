package jh.github.com.itemservice;

import jh.github.com.InventoryApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ContextConfiguration(classes = InventoryApplication.class)
@WebMvcTest(ItemController.class)
class ItemManagerApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("Should contain four elements")
	public void testItemArrSize() throws Exception {
		mvc.perform(MockMvcRequestBuilders
						.get("/items")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.items").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.items[*].id").isNotEmpty());
	}

}
