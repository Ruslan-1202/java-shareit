package ru.practicum.shareit.booking;

import java.util.Optional;

public interface BookingStorage {
    Optional<Booking> create(Booking booking);
}
