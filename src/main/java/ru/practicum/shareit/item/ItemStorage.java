package ru.practicum.shareit.item;

import ru.practicum.shareit.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    Optional<Item> create(Item item);

    Optional<Item> get(Long itemId);

    List<Item> getByUser(Long userId);

    void save(Item item);

    List<Item> search(Long userId, String text);

    Optional<Comment> saveComment(Comment comment);

    List<Comment> getCommentsByItem(Long itemId);
}
