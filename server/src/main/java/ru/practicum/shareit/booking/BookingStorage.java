package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.enumeration.BookingGetAll;
import ru.practicum.shareit.booking.enumeration.BookingState;
import ru.practicum.shareit.booking.enumeration.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface BookingStorage {
    Optional<Booking> create(Booking booking);

    Optional<Booking> get(long bookingId);

    Booking approve(Booking booking, BookingStatus bookingStatus);

    List<Booking> getAllByUser(long userId, BookingState bookingState, BookingGetAll bookingGetAll);

    Optional<Booking> getApproved(long userId, long itemId);

    List<Booking> getBokings(List<ItemDto> items);
}
