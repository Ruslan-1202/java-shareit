package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.StorageException;
import ru.practicum.shareit.exception.WrongUserException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public ItemDto create(Long userId, ItemDto itemDto) {
        User user = userStorage.get(userId)
                .orElseThrow(()->new NotFoundException("Пользователь не найден"));
        Item item = itemStorage.create(ItemMapper.toItem(user, itemDto))
                .orElseThrow(() -> new StorageException("Не удалось создать вещь"));
        return ItemMapper.toItemDto(item);
    }

    public ItemDto get(Long userId, Long itemId) {
        return ItemMapper.toItemDto(checkUserGetItem(userId, itemId));
    }

    public ItemDto change(Long userId, ItemDto itemDto) {
        Item item = checkUserGetItem(userId, itemDto.getId());
        return ItemMapper.toItemDto(item);
    }

    private Item checkUserGetItem(Long userId, Long itemId) {
        User user = userStorage.get(userId)
                .orElseThrow(()->new NotFoundException("Пользователь не найден"));
        Item item = itemStorage.get(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь не найдена"));
        if (!user.equals(item.getOwner())) {
            throw new WrongUserException("Пользователи не совпадают");
        }
        return item;
    }

    public List<ItemDto> getByUser(Long userId) {
        return null; //itemStorage.getByUser(userId);
    }
}
