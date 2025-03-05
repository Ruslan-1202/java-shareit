package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.enumeration.BookingState;
import ru.practicum.shareit.booking.enumeration.BookingStatus;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookingStorageImpl implements BookingStorage {
    private final BookingRepository bookingRepository;

    @Override
    public Optional<Booking> create(Booking booking) {
        return Optional.of(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public Optional<Booking> get(long bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @Override
    @Transactional
    public Booking approve(Booking booking, BookingStatus bookingStatus) {
        booking.setStatus(bookingStatus);
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public List<Booking> getAll(long userId, BookingState bookingState) {
        List<Booking> bookings;
        if (BookingState.WAITING.equals(bookingState) || BookingState.REJECTED.equals(bookingState)) {
            BookingStatus bookingStatus = BookingStatus.valueOf(bookingState.name());
            bookings = bookingRepository.findAllByItem_Owner_IdAndStatusOrderByStartDesc(userId, bookingStatus);
        } else {
            bookings = bookingRepository.findAllByItem_Owner_IdOrderByStartDesc(userId);
        }

        return bookings;
    }
}
