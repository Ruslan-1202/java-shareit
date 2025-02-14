package ru.practicum.shareit.item.strorage;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Optional;

public interface ItemStorage {
    Optional<ItemDto> create(ItemDto item);
}
