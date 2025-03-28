package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String name; // — краткое название;
    @NotBlank
    private String description; // — развёрнутое описание;
    @NotNull
    private Boolean available; // — статус о том, доступна или нет вещь для аренды;

    private BookingInfoDto lastBooking;

    private BookingInfoDto nextBooking;

    private List<CommentRetDto> comments;

    private Long requestId;
}
