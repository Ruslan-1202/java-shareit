package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.enumeration.BookingStatus;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User booker;
    private BookingStatus status;
}
