package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class ItemStorageImpl implements ItemStorage {
    private final HashMap<Long, Item> items = new HashMap<>();
    private Long counter = 0L;

    @Override
    public Optional<Item> create(Item item) {
        items.put(++counter, item);
        item.setId(counter);
        return Optional.of(item);
    }

    @Override
    public Optional<Item> get(Long itemId) {
        return Optional.ofNullable(items.get(itemId));
    }

    @Override
    public List<Item> getByUser(Long userId) {
        return items.values().stream()
                .filter(a -> a.getOwner().getId().equals(userId))
                .toList();
    }

    @Override
    public void save(Item item) {
        items.put(item.getId(), item);
    }

    @Override
    public List<Item> search(Long userId, String text) {
        if (text.isEmpty()) {
            return List.of();
        }
        return items.values().stream()
                .filter(a -> a.getName().toUpperCase().contains(text) || a.getDescription().toUpperCase().contains(text))
                .filter(a -> a.isAvailable())
                .toList();
    }
}
