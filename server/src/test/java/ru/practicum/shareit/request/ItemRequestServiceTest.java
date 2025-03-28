package ru.practicum.shareit.request;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.BaseTest;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestItemsDto;
import ru.practicum.shareit.user.UserMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
public class ItemRequestServiceTest extends BaseTest {
    private final EntityManager em;
    private final ItemRequestService service;

    @Test
    void getById() {

        ItemRequestItemsDto dtoByService = service.getById(itemRequest.getId());

        TypedQuery<ItemRequest> query = em.createQuery("select a from ItemRequest a where a.id = :id", ItemRequest.class);
        query.setParameter("id", itemRequest.getId());

        ItemRequestItemsDto dtoByQuery = new ItemRequestMapper().toItemRequestItemsDto(
                query.getSingleResult(),
                UserMapper.toUserDto(user),
                List.of()
        );

        assertEquals(dtoByService, dtoByQuery);
    }

    @Test
    void createItemRequest() {
        ItemRequestCreateDto dto = new ItemRequestCreateDto();
        dto.setDescription("Create Description");
        var dtoRet = service.create(user.getId(), dto);
        ItemRequestItemsDto itemRequestItemsDto = service.getById(dtoRet.getId());

        assertEquals(dtoRet.getDescription(), itemRequestItemsDto.getDescription());
    }

    @Test
    void getItemRequest() {
        var itemRequests = service.get(user.getId());
        assertEquals(itemRequests.size(), 1);
    }

    @Test
    void getAllItemRequest() {
        var itemRequests = service.getAll();
        assertEquals(itemRequests.size(), 1);
    }
}
