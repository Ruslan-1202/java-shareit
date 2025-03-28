package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestItemsDto;
import ru.practicum.shareit.request.dto.ItemRequestRetDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
@Slf4j
public class ItemRequestController {
    private static final String USER_HEADER = "X-Sharer-User-Id";

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestRetDto create(@RequestHeader(USER_HEADER) Long userId,
                                    @RequestBody ItemRequestCreateDto itemRequest) {
        log.debug("create for user id={}", userId);
        return itemRequestService.create(userId, itemRequest);
    }

    @GetMapping
    public List<ItemRequestRetDto> get(@RequestHeader(USER_HEADER) Long userId) {
        log.debug("get for user id={}", userId);
        return itemRequestService.get(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestRetDto> getAll() {
        log.debug("getAll");
        return itemRequestService.getAll();
    }

    @GetMapping("{id}")
    public ItemRequestItemsDto getById(@PathVariable Long id) {
        log.debug("getById for id={}", id);
        return itemRequestService.getById(id);
    }
}
