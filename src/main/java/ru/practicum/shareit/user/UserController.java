package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        log.debug("Создание пользователя");
        return userService.create(userDto);
    }

    @GetMapping("{id}")
    public UserDto get(@PathVariable Long id) {
        log.debug("Получение пользователя");
        return userService.get(id);
    }

    @PatchMapping("{id}")
    public UserDto update(@PathVariable Long id,
                          @Valid @RequestBody UserDto userDto) {
        log.debug("Обновление пользователя");
        return userService.save(id, userDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        log.debug("Удаление пользователя");
        userService.delete(id);
    }
}
