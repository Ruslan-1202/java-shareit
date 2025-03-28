package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.enumeration.BookingGetAll;
import ru.practicum.shareit.booking.enumeration.BookingState;
import ru.practicum.shareit.booking.enumeration.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookingStorageImpl implements BookingStorage {
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public Optional<Booking> create(Booking booking) {
        return Optional.of(bookingRepository.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<Booking> getAllByUser(long userId, BookingState bookingState, BookingGetAll bookingGetAll) {
        List<Booking> bookings;
        LocalDateTime dateTime = LocalDateTime.now();
        if (BookingState.WAITING.equals(bookingState) || BookingState.REJECTED.equals(bookingState)) {
            BookingStatus bookingStatus = BookingStatus.valueOf(bookingState.name());
            switch (bookingGetAll) {
                case OWNER ->
                        bookings = bookingRepository.findAllByItem_Owner_IdAndStatusOrderByStartDesc(userId, bookingStatus);
                default -> bookings = bookingRepository.findAllByBooker_IdAndStatusOrderByStartDesc(
                        userId,
                        bookingStatus
                );
            }
        } else if (BookingState.CURRENT.equals(bookingState)) {
            switch (bookingGetAll) {
                case OWNER -> bookings = bookingRepository.findByOwnerNow(userId, dateTime);
                default -> bookings = bookingRepository.findByBookerNow(userId, dateTime);
            }
        } else if (BookingState.PAST.equals(bookingState)) {
            switch (bookingGetAll) {
                case OWNER -> bookings = bookingRepository.findByOwnerPast(userId, dateTime);
                default -> bookings = bookingRepository.findByBookerPast(userId, dateTime);
            }
        } else if (BookingState.FUTURE.equals(bookingState)) {
            switch (bookingGetAll) {
                case OWNER -> bookings = bookingRepository.findByOwnerFuture(userId, dateTime);
                default -> bookings = bookingRepository.findByBookerFuture(userId, dateTime);
            }
        } else {
            switch (bookingGetAll) {
                case OWNER -> bookings = bookingRepository.findAllByItem_Owner_IdOrderByStartDesc(userId);
                default -> bookings = bookingRepository.findAllByBooker_IdOrderByStartDesc(userId);
            }
        }

        return bookings;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> getApproved(long userId, long itemId) {
        return Optional.ofNullable(bookingRepository.getApproved(userId, itemId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBokings(List<ItemDto> items) {
        return bookingRepository.getBokings(items.stream()
                .map(ItemDto::getId)
                .toList());
    }
}
