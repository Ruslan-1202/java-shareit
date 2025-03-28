package ru.practicum.shareit.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDto {
    private Long id;
    private String text;
    private Long authorId;
    private Long itemId;
    private LocalDateTime created;
}
