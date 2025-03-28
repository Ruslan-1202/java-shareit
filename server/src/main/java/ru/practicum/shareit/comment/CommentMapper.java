package ru.practicum.shareit.comment;

import ru.practicum.shareit.comment.dto.CommentCreateDto;
import ru.practicum.shareit.comment.dto.CommentRetDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

public class CommentMapper {
    public CommentRetDto toCommentRetDto(Comment comment) {
        return new CommentRetDto(comment.getId(),
                                 comment.getText(),
                                 comment.getAuthor().getName(),
                                 comment.getCreated()
        );
    }

    public Comment toComment(CommentCreateDto commentCreateDto, User user, Item item) {
        return new Comment(null,
                            commentCreateDto.getText(),
                            item,
                            user,
                            LocalDateTime.now());
    }
}
