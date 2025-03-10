package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.enumeration.BookingGetAll;
import ru.practicum.shareit.booking.enumeration.BookingState;
import ru.practicum.shareit.booking.enumeration.BookingStatus;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserStorage;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingStorage bookingStorage;
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;

    public BookingDto create(Long userId, BookingCreateDto bookingCreateDto) {
        checkDates(bookingCreateDto.getStart(), bookingCreateDto.getEnd());
        Booking booking = bookingStorage.create(
                        new BookingMapper().toBooking(
                                bookingCreateDto,
                                getItemAvilable(bookingCreateDto.getItemId()),
                                getUser(userId)
                        )
                )
                .orElseThrow(() -> new StorageException("Не удалось создать заказ"));
        return new BookingMapper().toBookingDto(booking);
    }

    public BookingDto approve(long userId, long bookingId, boolean approved) {
        Booking booking = getBooking(bookingId);
        if (booking.getItem().getOwner().getId() != userId) {
            throw new WrongUserException("Пользователь id=" + userId + " не владеет вещью");
        }

        checkUser(userId);

        BookingStatus bookingStatus;
        if (approved) {
            bookingStatus = BookingStatus.APPROVED;
        } else {
            bookingStatus = BookingStatus.REJECTED;
        }

        bookingStorage.approve(booking, bookingStatus);
        return new BookingMapper().toBookingDto(booking);
    }

    public BookingDto get(long bookingId) {
        return new BookingMapper().toBookingDto(getBooking(bookingId));
    }

    public List<BookingDto> getAllByBooker(long userId, String state) {
        BookingState bookingState = getBookingState(state);
        checkUser(userId);
        return bookingStorage.getAllByUser(userId, bookingState, BookingGetAll.BOOKER).stream()
                .map(a -> new BookingMapper().toBookingDto(a))
                .toList();
    }

    public List<BookingDto> getAllByOwner(long userId, String state) {
        BookingState bookingState = getBookingState(state);
        checkUser(userId);
        return bookingStorage.getAllByUser(userId, bookingState, BookingGetAll.OWNER).stream()
                .map(a -> new BookingMapper().toBookingDto(a))
                .toList();
    }

    private BookingState getBookingState(String state) {
        BookingState bookingState;
        try {
            bookingState = BookingState.valueOf(state);
        } catch (Exception e) {
            throw new WrongBookingStateException("Не удалось преобразовать state=" + state);
        }
        return bookingState;
    }

    private Booking getBooking(long bookingId) {
        return bookingStorage.get(bookingId)
                .orElseThrow(() -> new NotFoundException("Заказ с id=" + bookingId + " не найден"));
    }

    private void checkDates(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end) || start.isEqual(end)) {
            throw new WrongDatesException("Неправильные даты");
        }
    }

    private User getUser(Long userId) {
        return userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
    }

    private Item getItemAvilable(Long itemId) {
        Item item = itemStorage.get(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь id=" + itemId + " не найдена"));
        if (!item.isAvailable()) {
            throw new NotAvilableException("Вещь id=" + itemId + " недоступна");
        }
        return item;
    }

    private void checkUser(long userId) {
        getUser(userId);
    }
}
