package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.enumeration.BookingStatus;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private Long id; // — уникальный идентификатор бронирования;
    private LocalDateTime start; //— дата и время начала бронирования;
    private LocalDateTime end; //— дата и время конца бронирования;
    private Item item; //— вещь, которую пользователь бронирует;
    private User booker; //— пользователь, который осуществляет бронирование;
    private BookingStatus status;

}
