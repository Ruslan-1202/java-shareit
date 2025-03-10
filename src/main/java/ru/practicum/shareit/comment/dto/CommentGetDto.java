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
    private Long author_id;
    private Long item_id;
    private LocalDateTime created;
}
