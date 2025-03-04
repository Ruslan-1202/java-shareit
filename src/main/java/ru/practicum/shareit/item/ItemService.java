package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.dto.CommentCreateDto;
import ru.practicum.shareit.comment.dto.CommentRetDto;
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
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        Item item = itemStorage.create(ItemMapper.toItem(user, itemDto))
                .orElseThrow(() -> new StorageException("Не удалось создать вещь"));
        return ItemMapper.toItemDto(item);
    }

    public ItemDto get(Long userId, Long itemId) {
        checkUserExists(userId);
        return ItemMapper.toItemDto(getItem(itemId));
    }

    private void checkUserExists(Long userId) {
        userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
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
        return itemStorage.get(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь id=" + itemId + " не найдена"));
    }

    private User getUser(Long userId) {
        return userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
    }

    public CommentRetDto createComment(Long userId, Long itemId, CommentCreateDto commentCreateDto) {
        User user = getUser(userId);
        Item item = getItem(itemId);

        Comment comment = new CommentMapper().toComment(commentCreateDto, user, item);

        return new CommentMapper().toCommentRetDto(itemStorage.saveComment(comment)
                .orElseThrow(()->new StorageException("Не удалось сохранить комментарий")));
    }

    public List<CommentRetDto> getCommentsByItem(Long userId, Long itemId) {
        checkUserGetItem(userId, itemId);
        return itemStorage.getCommentsByItem(itemId).stream()
                .map(comment -> new CommentMapper().toCommentRetDto(comment))
                .toList();
    }
}
