package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    private static final String USER_HEADER = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@RequestHeader(USER_HEADER) long userId,
//                                    @Valid
                                    @RequestBody BookingCreateDto bookingCreateDto) {
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
    public BookingDto getById(@PathVariable long bookingId) {
        log.debug("getById id={}", bookingId);
        return bookingService.get(bookingId);
    }

    @GetMapping()
    public List<BookingDto> getAllByBooker(@RequestHeader(USER_HEADER) long userId,
                                           @RequestParam(defaultValue = "ALL") String state) {
        log.debug("getAllByUser userId={}, state={}", userId, state);
        return bookingService.getAllByBooker(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllByOwner(@RequestHeader(USER_HEADER) long userId,
                                          @RequestParam(defaultValue = "ALL") String state) {
        log.debug("getAllByOwner userId={}, state={}", userId, state);
        return bookingService.getAllByOwner(userId, state);
    }
}
