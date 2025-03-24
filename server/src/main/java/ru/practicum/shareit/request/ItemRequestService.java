package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.StorageException;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestRetDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserStorage;

@Service
@RequiredArgsConstructor
public class ItemRequestService {

    private final UserStorage userStorage;
    private final ItemRequestStorage itemRequestStorage;

    public ItemRequestRetDto create(Long userId, ItemRequestCreateDto itemRequestCreateDto) {
        User user = getUser(userId);

        ItemRequest itemRequest = itemRequestStorage.save(new ItemRequestMapper().toItemRequest(itemRequestCreateDto, user))
                .orElseThrow(() -> new StorageException("Не удалось создать запрос"));
        return new ItemRequestMapper().toItemRequestRetDto(itemRequest, UserMapper.toUserDto(user));
    }


    private User getUser(long userId) {
        return userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + userId + " не найден"));
    }
}
