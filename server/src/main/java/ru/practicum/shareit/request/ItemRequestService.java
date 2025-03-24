package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.StorageException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestItemsDto;
import ru.practicum.shareit.request.dto.ItemRequestRetDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserStorage;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestService {

    private final UserStorage userStorage;
    private final ItemRequestStorage itemRequestStorage;
    private final ItemStorage itemStorage;

    private User getUser(long userId) {
        return userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + userId + " не найден"));
    }

    public ItemRequestRetDto create(Long userId, ItemRequestCreateDto itemRequestCreateDto) {
        User user = getUser(userId);

        ItemRequest itemRequest = itemRequestStorage.save(new ItemRequestMapper().toItemRequest(itemRequestCreateDto, user))
                .orElseThrow(() -> new StorageException("Не удалось создать запрос"));
        return new ItemRequestMapper().toItemRequestRetDto(itemRequest, UserMapper.toUserDto(user));
    }

    public List<ItemRequestRetDto> get(Long userId) {
        UserDto userDto = UserMapper.toUserDto(getUser(userId));
        return itemRequestStorage.getByUser(userId).stream()
                .sorted(Comparator.comparing(ItemRequest::getCreated).reversed())
                .map(a -> new ItemRequestMapper().toItemRequestRetDto(a, userDto))
                .toList();
    }

    public List<ItemRequestRetDto> getAll() {
        return itemRequestStorage.getAll().stream()
                .map(a -> new ItemRequestMapper().toItemRequestRetDto(a, UserMapper.toUserDto(a.getRequestor())))
                .toList();
    }

    public ItemRequestItemsDto getById(Long id) {
        ItemRequest itemRequest = itemRequestStorage.getById(id);
        List<ItemDto> items = itemStorage.getByRequest(id).stream()
                .map(ItemMapper::toItemDto)
                .toList();

        return new ItemRequestMapper().toItemRequestItemsDto(
                itemRequest,
                UserMapper.toUserDto(itemRequest.getRequestor()),
                items
        );
    }
}
