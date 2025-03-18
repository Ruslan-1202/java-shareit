package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingInfoDto;
import ru.practicum.shareit.booking.enumeration.BookingStatus;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

public class BookingMapper {
    public Booking toBooking(BookingCreateDto bookingCreateDto, Item item, User user) {
        return new Booking(
                null,
                bookingCreateDto.getStart(),
                bookingCreateDto.getEnd(),
                item,
                user,
                BookingStatus.WAITING
        );
    }

    public BookingDto toBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem(),
                booking.getBooker(),
                booking.getStatus()
        );
    }

    public BookingInfoDto toBookingInfoDto(Booking booking) {
        return new BookingInfoDto(
                booking.getId(),
                booking.getBooker().getId(),
                booking.getStart(),
                booking.getEnd()
        );
    }
}
