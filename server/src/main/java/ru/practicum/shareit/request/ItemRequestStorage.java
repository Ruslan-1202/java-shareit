package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemRequestStorage {
    private final ItemRequestRepository requestRepository;

    public Optional<ItemRequest> save(ItemRequest itemRequest) {
        return Optional.of(requestRepository.save(itemRequest));
    }

    public List<ItemRequest> getByUser(long userId) {
        return requestRepository.findItemRequestsByRequestorId(userId);
    }

    public List<ItemRequest> getAll() {
        return requestRepository.findAll(Sort.by(Sort.Direction.DESC, "created"));
    }

    public ItemRequest getById(long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item id=" + id + " not found"));
    }
}
