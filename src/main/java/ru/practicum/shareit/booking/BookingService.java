package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotAvilableException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.StorageException;
import ru.practicum.shareit.exception.WrongDatesException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserStorage;

import java.time.LocalDateTime;

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
}
