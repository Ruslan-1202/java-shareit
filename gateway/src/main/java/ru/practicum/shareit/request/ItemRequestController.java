package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
@Slf4j
public class ItemRequestController {

    private static final String USER_HEADER = "X-Sharer-User-Id";

    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(USER_HEADER) Long userId,
                                         @RequestBody ItemRequestCreateDto itemRequest) {
        log.debug("create for user id={}", userId);
        return itemRequestClient.create(userId, itemRequest);
    }

    @GetMapping
    public ResponseEntity<Object> get(@RequestHeader(USER_HEADER) Long userId) {
        log.debug("get for user id={}", userId);
        return itemRequestClient.get(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        log.debug("getAll");
        return itemRequestClient.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@RequestHeader(USER_HEADER) Long userId,
                                          @PathVariable Long id) {
        log.debug("getById for id={}, userId={} ", id, userId);
        return itemRequestClient.getById(userId, id);
    }
}
