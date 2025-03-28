package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.WrongUserException;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingControllerTest {
    private static final String URL = "/bookings";
    @MockBean
    private BookingService service;

    @Autowired
    private MockMvc mvc;

    private BookingDto dto;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        dto = new BookingDto();
        dto.setId(1L);
    }

    @Test
    void saveNewBooking() throws Exception {
        when(service.create(any(), any()))
                .thenReturn(dto);

        mvc.perform(post(URL)
                        .content(mapper.writeValueAsString(dto))
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId()), Long.class));
    }

    @Test
    void getAllByBooker() throws Exception {
        String state = "ALL";
        long userId = 1L;

        when(service.getAllByBooker(userId, state)).thenReturn(Collections.emptyList());

        mvc.perform(get(URL)
                        .header("X-Sharer-User-Id", userId)
                        .param("state", state))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, times(1)).getAllByBooker(userId, state);
    }

    @Test
    void getAllByOwner() throws Exception {
        String state = "CURRENT";
        long userId = 1L;

        when(service.getAllByOwner(userId, state)).thenReturn(Collections.emptyList());

        mvc.perform(get(URL + "/owner")
                        .header("X-Sharer-User-Id", userId)
                        .param("state", state))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, times(1)).getAllByOwner(userId, state);
    }

    @Test
    void approveById() throws Exception {
        long userId = 1L;
        long bookingId = 1L;

        when(service.approve(userId, bookingId, true)).thenReturn(dto);

        mvc.perform(patch(URL + "/1")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());

        verify(service, times(1)).approve(userId, bookingId, true);
    }

    @Test
    void getById() throws Exception {
        when(service.get(dto.getId()))
                .thenReturn(dto);

        mvc.perform(get(URL + "/1")
                        .header("X-Sharer-User-Id", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId()), Long.class));
    }

    @Test
    void approveWithException() throws Exception {
        when(service.approve(1L, 1L, true)).thenThrow(WrongUserException.class);

        mvc.perform(patch(URL + "/1")
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(500));
    }
}
