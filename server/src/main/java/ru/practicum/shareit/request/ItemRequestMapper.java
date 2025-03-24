package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestRetDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

public class ItemRequestMapper {
    public ItemRequest toItemRequest(ItemRequestCreateDto itemRequestCreateDto, User user) {
        return new ItemRequest(
                null,
                itemRequestCreateDto.getDescription(),
                user,
                LocalDateTime.now()
        );
    }

    public ItemRequestRetDto toItemRequestRetDto(ItemRequest itemRequest, UserDto userDto) {
        return new ItemRequestRetDto(
                itemRequest.getId(),
                itemRequest.getDescription(),
                userDto,
                itemRequest.getCreated()
        );
    }
}
