package jh.github.com.itemservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
class ItemManagerApplicationTests {

	@Mock
	ItemRepository itemRepository;

	@Test
	@DisplayName("Should contain two elements")
	public void givenValidInitData_whenCallingFindAll_thenReturnData() {
		List<Item> items = itemRepository.findAll();

		assertThat(items, hasSize(2));
	}

}
