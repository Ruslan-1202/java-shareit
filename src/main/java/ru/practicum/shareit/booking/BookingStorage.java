package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.enumeration.BookingState;
import ru.practicum.shareit.booking.enumeration.BookingStatus;

import java.util.List;
import java.util.Optional;

public interface BookingStorage {
    Optional<Booking> create(Booking booking);

    Optional<Booking> get(long bookingId);

    Booking approve(Booking booking, BookingStatus bookingStatus);

    List<Booking> getAll(long userId, BookingState bookingState);
}
