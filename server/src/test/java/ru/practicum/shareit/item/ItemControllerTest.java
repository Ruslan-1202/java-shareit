package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.item.dto.ItemDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
    @Mock
    private ItemService service;

    @InjectMocks
    private ItemController controller;

    private MockMvc mvc;

    private ItemDto dto;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUpController() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        dto = new ItemDto();
        dto.setId(1L);
        dto.setName("Test");
        dto.setDescription("Test Descr");
        dto.setAvailable(true);
    }

    @Test
    void saveNewItem() throws Exception {
        when(service.create(any(), any()))
                .thenReturn(dto);

        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(dto))
                        .header("X-Sharer-User-Id", dto.getId().toString())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.available", is(dto.getAvailable())))
        ;
    }

    @Test
    void getItem() throws Exception {
        when(service.get(any(), any()))
                .thenReturn(dto);

        mvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", dto.getId().toString())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.available", is(dto.getAvailable())))
        ;

        verify(service, times(1)).get(any(), any());
    }

    @Test
    void getItemsByUser() throws Exception {
        when(service.getByUser(any()))
                .thenReturn(List.of());

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", dto.getId().toString())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
        ;

        verify(service, times(1)).getByUser(any());
    }

    @Test
    void searchItem() throws Exception {
        when(service.searchItem(any(), any()))
                .thenReturn(List.of());

        mvc.perform(get("/items/search?text=Test")
                        .header("X-Sharer-User-Id", dto.getId().toString())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
        ;

        verify(service, times(1)).searchItem(any(), any());
    }

    @Test
    void patchItem() throws Exception {
        when(service.change(any(), any()))
                .thenReturn(dto);

        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", dto.getId().toString())
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.available", is(dto.getAvailable())))
        ;

        verify(service, times(1)).change(any(), any());
    }
}
