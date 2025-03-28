package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.enumeration.BookingStatus;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

public class BaseTest {
    public User user;
    public Item item;
    public ItemRequest itemRequest;
    public Booking booking;

    @BeforeEach
    void setUp() {
        user = new User(
                1L,
                "John",
                "john.doe@mail.com");

        item = new Item();
        item.setId(1L);
        item.setName("Item name");
        item.setDescription("Item descr");
        item.setAvailable(true);
        item.setOwner(user);

        itemRequest = new ItemRequest();
        itemRequest.setId(1L);
        itemRequest.setDescription("Req Descr");
        itemRequest.setRequestor(user);
        itemRequest.setCreated(LocalDateTime.of(2024, 3, 22, 0, 0, 0));

        booking = new Booking(1L, LocalDateTime.of(2012, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 3, 23, 0, 0, 0),
                item, user, BookingStatus.WAITING);
    }
}
