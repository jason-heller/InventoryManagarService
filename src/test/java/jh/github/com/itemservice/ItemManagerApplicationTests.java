package jh.github.com.itemservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ItemManagerApplicationTests {

	@Autowired
	private ItemController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void itemArrSize() {
		controller.createItem(new Item(null, "A", "B", "C", new BigDecimal(1)));
		List<Item> items = controller.getAllItems();

		assertThat(items).hasSize(4);
	}
}
