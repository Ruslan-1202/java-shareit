package ru.practicum.shareit.item;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.BaseTest;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.dto.CommentCreateDto;
import ru.practicum.shareit.comment.dto.CommentRetDto;
import ru.practicum.shareit.exception.StorageException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemPatchDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
class ItemServiceTest extends BaseTest {
    private final EntityManager em;
    private final ItemService service;

    @Test
    void getItemById() {

        ItemDto dtoByService = service.get(user.getId(), item.getId());

        TypedQuery<Item> query = em.createQuery("select a from Item a where a.id = :itemId", Item.class);
        query.setParameter("itemId", item.getId());

        ItemDto dtoByQuery = ItemMapper.toItemDto(query.getSingleResult());

        assertEquals(dtoByService, dtoByQuery);
    }

    @Test
    void getItemByUser() {
        List<ItemDto> dtoByService = service.getByUser(user.getId());

        TypedQuery<Item> query = em.createQuery("select a from Item a where a.owner = :user", Item.class);
        query.setParameter("user", user);

        List<ItemDto> dtoByQuery = query.getResultList().stream().map(ItemMapper::toItemDto).toList();

        assertEquals(dtoByService, dtoByQuery);
    }

    @Test
    void search() {
        List<ItemDto> dtoByService = service.searchItem(user.getId(), "tem");

        TypedQuery<Item> query = em.createQuery("select i from Item i\n" + "            where i.available = true\n" + "              and (\n" + "                 upper(i.name) like upper(concat('%', :text, '%'))\n" + "                 or upper(i.description) like upper(concat('%', :text, '%'))\n" + "                  )", Item.class);
        query.setParameter("text", "tem");

        List<ItemDto> dtoByQuery = List.of(ItemMapper.toItemDto(query.getSingleResult()));

        assertEquals(dtoByService, dtoByQuery);
    }

    @Test
    void getCommentsByItem() {
        List<CommentRetDto> dtoByService = service.getCommentsByItem(user.getId(), item.getId());

        TypedQuery<Comment> query = em.createQuery("select  c.id,\n" + "                            c.text,\n" + "                            c.author,\n" + "                            c.item,\n" + "                            c.created\n" + "                      from Comment c\n" + "                     where c.item = :id", Comment.class);
        query.setParameter("id", item);

        List<CommentRetDto> dtoByQuery = query.getResultList().stream().map(comment -> new CommentMapper().toCommentRetDto(comment)).toList();

        assertEquals(dtoByService, dtoByQuery);
    }

    @Test
    void createComment() {
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        commentCreateDto.setText("Test comm");

        CommentRetDto dtoByService = service.createComment(user.getId(), item.getId(), commentCreateDto);
        CommentRetDto dtoByQuery = new CommentMapper().toCommentRetDto(new CommentMapper().toComment(commentCreateDto, user, item));

        assertEquals(dtoByService.getText(), dtoByQuery.getText());
    }

    @Test
    void updateItem() {
        ItemPatchDto newItem = new ItemPatchDto();
        newItem.setId(item.getId());
        newItem.setName("New test name");

        ItemDto itemDto = service.change(user.getId(), newItem);

        Item itemMap = ItemMapper.toItemFromItemPatchDto(item, newItem);

        assertEquals(newItem.getName(), itemDto.getName());
        assertEquals(newItem.getName(), itemMap.getName());
    }

    @Test
    void createItem() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("New test name");
        itemDto.setAvailable(true);
        itemDto.setDescription("New test description");

        ItemDto newItemDto = service.create(user.getId(), itemDto);

        itemDto.setId(newItemDto.getId());

        assertEquals(itemDto, newItemDto);
    }

    @Test
    void storageException() {
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        commentCreateDto.setText("Test comm");

        ItemService mockItemService = Mockito.mock(ItemService.class);

        Mockito.when(mockItemService.createComment(Mockito.anyLong(), Mockito.anyLong(), Mockito.any())).thenThrow(new StorageException("Ошибка при доступе к базе"));

        final StorageException exception = Assertions.assertThrows(StorageException.class, () -> mockItemService.createComment(user.getId(), item.getId(), commentCreateDto));

        assertEquals("Ошибка при доступе к базе", exception.getMessage());
    }
}
