package ru.practicum.shareit.item.strorage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Optional;

@Component
public class ItemStorageInMemory implements ItemStorage {
    @Override
    public Optional<ItemDto> create(ItemDto item) {
        return Optional.empty();
    }
}
