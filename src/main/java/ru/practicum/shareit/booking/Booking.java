package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.enumeration.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

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
    private BookingStatus status;  //— статус бронирования. Может принимать одно из следующих
//    значений: WAITING — новое бронирование, ожидает одобрения, APPROVED —
//    Дополнительные советы ментора 2
//    бронирование подтверждено владельцем, REJECTED — бронирование
//    отклонено владельцем, CANCELED — бронирование отменено создателем.

}
