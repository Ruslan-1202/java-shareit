package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.StorageException;
import ru.practicum.shareit.exception.WrongUserException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemPatchDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserStorage;

import java.util.List;

import static ru.practicum.shareit.item.ItemMapper.toItemDto;
import static ru.practicum.shareit.item.ItemMapper.toItemFromItemPatchDto;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public ItemDto create(Long userId, ItemDto itemDto) {
        User user = userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Item item = itemStorage.create(ItemMapper.toItem(user, itemDto))
                .orElseThrow(() -> new StorageException("Не удалось создать вещь"));
        return ItemMapper.toItemDto(item);
    }

    public ItemDto get(Long userId, Long itemId) {
        getUser(userId); //проверим, что пользак есть
        return ItemMapper.toItemDto(getItem(itemId));
    }

    public ItemDto change(Long userId, ItemPatchDto itemDto) {
        Item item = checkUserGetItem(userId, itemDto.getId());
        item = toItemFromItemPatchDto(item, itemDto);
        itemStorage.save(item);
        return ItemMapper.toItemDto(item);
    }

    public List<ItemDto> getByUser(Long userId) {
        return itemStorage.getByUser(userId).stream()
                .map(item -> toItemDto(item))
                .toList();
    }

    public List<ItemDto> searchItem(Long userId, String text) {
        return itemStorage.search(userId, text).stream()
                .map(item -> toItemDto(item))
                .toList();
    }


    private Item checkUserGetItem(Long userId, Long itemId) {
        User user = getUser(userId);
        Item item = getItem(itemId);
        if (!user.equals(item.getOwner())) {
            throw new WrongUserException("Пользователи не совпадают");
        }
        return item;
    }

    private Item getItem(Long itemId) {
        Item item = itemStorage.get(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь не найдена"));
        return item;
    }

    private User getUser(Long userId) {
        User user = userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return user;
    }
}
