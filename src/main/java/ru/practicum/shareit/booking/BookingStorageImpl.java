package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookingStorageImpl implements BookingStorage {
    private final BookingRepository bookingRepository;

    @Override
    public Optional<Booking> create(Booking booking) {
        return Optional.of(bookingRepository.save(booking));
    }
}
