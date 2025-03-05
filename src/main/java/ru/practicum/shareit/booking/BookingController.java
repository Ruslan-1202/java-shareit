package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    private static final String USER_HEADER = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@RequestHeader(USER_HEADER) long userId,
                                    @Valid @RequestBody BookingCreateDto bookingCreateDto) {
        log.debug("createBooking: {}", bookingCreateDto);
        return bookingService.create(userId, bookingCreateDto);
    }

    @PatchMapping("{bookingId}")
    public BookingDto approveById(@RequestHeader(USER_HEADER) long userId,
                              @PathVariable long bookingId,
                              @RequestParam(value = "approved", defaultValue = "true") boolean approved) {
        log.debug("approveById id={}, {}", bookingId, approved);
        return bookingService.approve(userId, bookingId, approved);
    }

    @GetMapping("{bookingId}")
    public BookingDto getById(@RequestHeader(USER_HEADER) long userId,
                              @PathVariable long bookingId) {
        log.debug("getById id={}", bookingId);
        return bookingService.get(userId, bookingId);
    }

    @GetMapping()
    public List<BookingDto> getAll(@RequestHeader(USER_HEADER) long userId,
                                   @RequestParam(defaultValue = "ALL") String state) {
        log.debug("getAll id={}, state={}", userId, state);
        return bookingService.getAll(userId, state);
    }
}
