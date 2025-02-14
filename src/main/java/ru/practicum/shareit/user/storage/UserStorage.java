package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.dto.UserDto;

public interface UserStorage {
    UserDto create(UserDto user);
}
