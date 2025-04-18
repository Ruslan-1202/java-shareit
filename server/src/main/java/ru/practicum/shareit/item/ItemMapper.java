package ru.practicum.shareit.item;

import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemPatchDto;
import ru.practicum.shareit.user.User;

import java.util.Collections;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                null,
                null,
                Collections.emptyList(),
                item.getRequestId()
        );
    }

    public static Item toItem(User user, ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(user);
        item.setRequestId(itemDto.getRequestId());

        return item;
    }

    public static Item toItemFromItemPatchDto(Item oldItem, ItemPatchDto itemPatchDto) {
        if (oldItem == null) {
            throw new NotFoundException("Не найден объект для изменения");
        }

        Item newItem = new Item(oldItem.getId(),
                oldItem.getName(),
                oldItem.getDescription(),
                oldItem.isAvailable(),
                oldItem.getOwner(),
                oldItem.getRequestId()
        );

        if (itemPatchDto.getName() != null) {
            newItem.setName(itemPatchDto.getName());
        }
        if (itemPatchDto.getDescription() != null) {
            newItem.setDescription(itemPatchDto.getDescription());
        }
        if (itemPatchDto.getAvailable() != null) {
            newItem.setAvailable(itemPatchDto.getAvailable());
        }

        return newItem;
    }
}
