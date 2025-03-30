package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemPatchDto;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
@Validated
public class ItemController {
    private static final String USER_HEADER = "X-Sharer-User-Id";
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader(USER_HEADER) long userId,
                                             @Valid @RequestBody ItemDto itemDto) {
        log.debug("createItem by userId={} for item={}", userId, itemDto);
        return itemClient.create(userId, itemDto);
    }

    @GetMapping("{itemId}")
    public ResponseEntity<Object> getItem(@RequestHeader(USER_HEADER) long userId,
                                          @PathVariable("itemId") long itemId) {
        log.debug("getItem by id={} userId={}", itemId, userId);
        return itemClient.getItem(userId, itemId);
    }

    @GetMapping()
    public ResponseEntity<Object> getItemsByUser(@RequestHeader(USER_HEADER) long userId) {
        log.debug("getItemsByUser by userId={}", userId);
        return itemClient.getByUser(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItem(@RequestHeader(USER_HEADER) long userId,
                                             @RequestParam("text") String text) {
        log.debug("searchItem by text=[{}]", text);
        return itemClient.searchItem(userId, text);
    }

    @PatchMapping("{itemId}")
    public ResponseEntity<Object> patchItem(@RequestHeader(USER_HEADER) long userId,
                                            @PathVariable("itemId") long itemId,
                                            @RequestBody ItemPatchDto item) {
        log.debug("patchItem by userId={}, id={}, for item=[{}]", userId, itemId, item);
        return itemClient.change(userId, itemId, item);
    }

    @PostMapping("{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader(USER_HEADER) long userId,
                                                @PathVariable("itemId") long itemId,
                                                @Valid @RequestBody CommentCreateDto comment) {
        log.debug("createComment by userId={}, itemId={} for comment=[{}]", userId, itemId, comment);
        return itemClient.createComment(userId, itemId, comment);
    }

    @GetMapping("comments/{itemId}")
    public ResponseEntity<Object> getCommentsByItem(@RequestHeader(USER_HEADER) long userId,
                                                    @PathVariable("itemId") long itemId) {
        log.debug("getCommentsByItem by userId={}, itemId={}", userId, itemId);
        return itemClient.getCommentsByItem(userId, itemId);
    }
}
