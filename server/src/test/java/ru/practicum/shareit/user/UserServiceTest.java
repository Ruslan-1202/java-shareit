package ru.practicum.shareit.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.BaseTest;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
//@TestClassOrder(ClassOrderer.OrderAnnotation.class)
//@TestMethodOrder(MethodOrderer.MethodName.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest extends BaseTest {
    private final EntityManager em;
    private final UserService service;

    private static User newUser;

    @Test
    @Order(1)
    void getById() {

        UserDto dtoByService = service.get(user.getId());

        TypedQuery<User> query = em.createQuery("select a from User a where a.id = :userId", User.class);
        query.setParameter("userId", user.getId());

        UserDto dtoByQuery = UserMapper.toUserDto(query.getSingleResult());

        assertEquals(dtoByService, dtoByQuery);
    }

    @Test
    @Order(2)
    void createUser() {
        UserCreateDto userCreateDto = new UserCreateDto("John", "Doe");

        UserDto userDto = service.create(userCreateDto);

        UserDto dtoByService = service.get(userDto.getId());

        newUser = UserMapper.toUser(userCreateDto);
        newUser.setId(dtoByService.getId());
        assertEquals(userDto, dtoByService);
    }

    @Test
    @Order(3)
    void saveUser() {
        UserDto secondUser = service.get(2L);
        secondUser.setName("Changed");
        UserDto savedUser = service.save(secondUser.getId(), secondUser);

        assertEquals(savedUser, secondUser);
    }

    @Test
    @Order(4)
    void deleteUser() {
        UserDto secondUser = service.get(2L);
        service.delete(secondUser.getId());
        UserDto dtoByService;
        try {
            dtoByService = service.get(secondUser.getId());
        } catch (Exception e) {
            dtoByService = null;
        }

        Assertions.assertNull(dtoByService);
    }
}
