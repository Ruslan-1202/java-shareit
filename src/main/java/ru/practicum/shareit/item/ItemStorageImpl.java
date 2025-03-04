package ru.practicum.shareit.item;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemStorageImpl implements ItemStorage {
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Optional<Item> create(Item item) {
        return Optional.of(itemRepository.save(item));
    }

    @Override
    public Optional<Item> get(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Override
    public List<Item> getByUser(Long userId) {
        return itemRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    public List<Item> search(Long userId, String text) {
        if (text.isEmpty()) {
            return List.of();
        }
        return itemRepository.search(text);
    }

    @Override
    public Optional<Comment> saveComment(Comment comment) {
        return Optional.of(commentRepository.save(comment));
    }

    @Override
    public List<Comment> getCommentsByItem(Long itemId) {
        return commentRepository.findByItem_Id(itemId);
    }
}
