package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.StorageException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.strorage.ItemStorage;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemStorage itemStorage;

    public ItemDto create(ItemDto item) {
        return itemStorage.create(item)
                .orElseThrow(() -> new StorageException("Не удалось создать вещь"));
    }
}
