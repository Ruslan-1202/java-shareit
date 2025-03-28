package ru.practicum.shareit.booking;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.BaseTest;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.enumeration.BookingState;
import ru.practicum.shareit.booking.enumeration.BookingStatus;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
class BookingServiceTest extends BaseTest {
    private final EntityManager em;
    private final BookingService bookingService;
    private final BookingMapper bookingMapper = new BookingMapper();

      @Test
    void getBooking() {
        BookingDto bookingDto = bookingService.get(booking.getId());

        assertEquals(bookingDto, new BookingMapper().toBookingDto(booking));
    }

    @Test
    void createBooking() {
        BookingCreateDto bookingCreateDto = new BookingCreateDto();
        bookingCreateDto.setItemId(1L);
        bookingCreateDto.setStart(LocalDateTime.now().minusDays(1));
        bookingCreateDto.setEnd(LocalDateTime.now().plusDays(1));

        BookingDto bookingDto = bookingService.create(user.getId(), bookingCreateDto);

        BookingDto bookingSrv = bookingService.get(bookingDto.getId());
        assertEquals(bookingDto, bookingSrv);
    }

    @Test
    void getAllByOwner() {
        List<BookingDto> bookingDtos = bookingService.getAllByOwner(user.getId(), "ALL");
        assertEquals(2, bookingDtos.size());
    }

    @Test
    void approveBooking() {
        BookingDto bookingDto = bookingService.approve(user.getId(), booking.getId(), true);
        assertEquals(bookingDto.getStatus(), BookingStatus.APPROVED);
    }

    @Test
    void getAllByBooker() {
        long userId = 1L;
        User user = new User(
                1L,
                "John",
                "john.doe@mail.com");

        BookingState bookingState = BookingState.ALL;

        List<BookingDto> bookingDtos = bookingService.getAllByBooker(userId, bookingState.toString());

        TypedQuery<Booking> query = em.createQuery("select b from Booking b where b.booker = :user", Booking.class);
        query.setParameter("user", user);
        List<BookingDto> bookingQuery = query.getResultList().stream()
                .map(bookingMapper::toBookingDto)
                .toList();

        bookingState = BookingState.REJECTED;
        List<BookingDto> bookingRej = bookingService.getAllByBooker(userId, bookingState.toString());

        bookingState = BookingState.FUTURE;
        List<BookingDto> bookingFuture = bookingService.getAllByBooker(userId, bookingState.toString());

        bookingState = BookingState.CURRENT;
        List<BookingDto> bookingCurrent = bookingService.getAllByBooker(userId, bookingState.toString());

        bookingState = BookingState.PAST;
        List<BookingDto> bookingPast = bookingService.getAllByBooker(userId, bookingState.toString());

        assertEquals(bookingDtos, bookingQuery);
        assertEquals(bookingRej, List.of());
        assertEquals(bookingFuture, List.of());
        assertEquals(bookingCurrent, List.of());
        assertEquals(bookingPast, bookingQuery);

    }
}
