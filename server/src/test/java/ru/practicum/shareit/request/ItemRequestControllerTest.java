package ru.practicum.shareit.request;

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
import ru.practicum.shareit.request.dto.ItemRequestRetDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ItemRequestControllerTest {

    private static final String URL = "/requests";
    @Mock
    private ItemRequestService service;

    @InjectMocks
    private ItemRequestController controller;

    private MockMvc mvc;

    private ItemRequestRetDto dto;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        dto = new ItemRequestRetDto();
        dto.setId(1L);
        dto.setDescription("Test Descr");
    }

    @Test
    void saveNewItemRequest() throws Exception {
        when(service.create(any(), any()))
                .thenReturn(dto);

        mvc.perform(post(URL)
                        .content(mapper.writeValueAsString(dto))
                        .header("X-Sharer-User-Id", dto.getId().toString())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(dto.getDescription())))
        ;
    }

    @Test
    void getItemRequest() throws Exception {
        when(service.get(any()))
                .thenReturn(List.of());

        mvc.perform(get(URL)
                        .header("X-Sharer-User-Id", dto.getId().toString())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
        ;

        verify(service, times(1)).get(any());
    }

    @Test
    void getAllItemRequest() throws Exception {
        when(service.getAll())
                .thenReturn(List.of());

        mvc.perform(get(URL + "/all")
                        .header("X-Sharer-User-Id", dto.getId().toString())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, times(1)).getAll();
    }
}
