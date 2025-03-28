package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingInfoDto;
import ru.practicum.shareit.comment.dto.CommentRetDto;

import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id; // — уникальный идентификатор вещи;

    private String name; // — краткое название;

    private String description; // — развёрнутое описание;

    private Boolean available; // — статус о том, доступна или нет вещь для аренды;

    private BookingInfoDto lastBooking;

    private BookingInfoDto nextBooking;

    private List<CommentRetDto> comments;

    private Long requestId;
}
