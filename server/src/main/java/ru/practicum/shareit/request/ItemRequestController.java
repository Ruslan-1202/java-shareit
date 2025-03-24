package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestRetDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    //    POST /requests
//    GET /requests  получить список своих запросов вместе с данными об ответах на них.
//    GET /requests/all получить список запросов, созданных другими пользователями.
//    GET /requests/{requestId}
    private static final String USER_HEADER = "X-Sharer-User-Id";

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestRetDto post(@RequestHeader(USER_HEADER) Long userId,
                                  @RequestBody ItemRequestCreateDto itemRequest) {
        return itemRequestService.create(userId, itemRequest);
    }

    @GetMapping
    public List<ItemRequestCreateDto> get(@RequestHeader(USER_HEADER) Long userId) {
        return new ArrayList<>();
    }

    @GetMapping("{id}")
    public ItemRequestCreateDto getById(@RequestHeader(USER_HEADER) Long userId,
                                        @PathVariable Long id) {
        return new ItemRequestCreateDto();
    }
}
