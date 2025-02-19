package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemPatchDto;

import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private static final String USER_HEADER = "X-Sharer-User-Id";
    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestHeader(USER_HEADER) Long userId,
                              @Valid @RequestBody ItemDto item) {
        return itemService.create(userId, item);
    }

    @GetMapping("{itemId}")
    public ItemDto getItem(@RequestHeader(USER_HEADER) Long userId,
                           @PathVariable("itemId") Long itemId) {
        return itemService.get(userId, itemId);
    }

    @GetMapping()
    public List<ItemDto> getItemsByUser(@RequestHeader(USER_HEADER) Long userId) {
        return itemService.getByUser(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestHeader(USER_HEADER) Long userId,
                                    @RequestParam("text") String text) {
        return itemService.searchItem(userId, text);
    }

    @PatchMapping("{itemId}")
    public ItemDto patchItem(@RequestHeader(USER_HEADER) Long userId,
                             @PathVariable("itemId") Long itemId,
                             @RequestBody ItemPatchDto item) {
        item.setId(itemId);
        return itemService.change(userId, item);
    }
}
