package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class ItemStorageInMemory implements ItemStorage {
    private HashMap<Long, Item> items = new HashMap<>();
    private Long counter = 0L;

    @Override
    public Optional<Item> create(Item item) {
        checks(item);
        items.put(++counter, item);
        item.setId(counter);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Item> get(Long itemId) {
        return Optional.ofNullable(items.get(itemId));
    }

    @Override
    public List<Item> getByUser(Long userId) {
        return items.values().stream()
                .filter(a->a.getOwner().getId().equals(userId))
                .toList();
    }

    private void checks(Item item) {
        if (item.isAvailable() ) {

        }
    }
}
