package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;

@Component
public class UserStorageInMemory implements UserStorage {
    @Override
    public UserDto create(UserDto user) {
        return null;
    }
}
