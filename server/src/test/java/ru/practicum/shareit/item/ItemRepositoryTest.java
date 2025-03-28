package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.user.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
//@Import({ItemRepository.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRepositoryTest {

    static User userTest = new User(1L, "John", "Doe@");
    static Item itemTest = new Item(1L, "Item name", "Item descr", true, userTest, null);

    private final ItemRepository repository;

    @Test
    void findById() {
        List<Item> items = repository.findByUserId(userTest.getId());
        Item item = items.get(0);
        assertThat(item.getId(), notNullValue());
        assertThat(item.getName(), equalTo(itemTest.getName()));
    }
}
