package ru.practicum.shareit.booking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.enumeration.BookingStatus;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings", schema = "public")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // — уникальный идентификатор бронирования;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime start; //— дата и время начала бронирования;
    @Column(name = "end_date", nullable = false)
    private LocalDateTime end; //— дата и время конца бронирования;
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item; //— вещь, которую пользователь бронирует;
    @ManyToOne(fetch = FetchType.LAZY)
    private User booker; //— пользователь, который осуществляет бронирование;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
