package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemRequestStorage {
    private final ItemRequestRepository requestRepository;

    public Optional<ItemRequest> save(ItemRequest itemRequest) {
        return Optional.of(requestRepository.save(itemRequest));
    }
}
