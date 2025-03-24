package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        log.debug("createUser [{}]", userCreateDto);
        return userClient.create(userCreateDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        log.debug("getUser id={}", id);
        return userClient.getUser(id);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id,
                                             @Valid @RequestBody UserDto userDto) {
        log.debug("updateUser id={}, for [{}]", id, userDto);
        return userClient.change(id, userDto);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        log.debug("deleteUser by id={}", id);
        userClient.delete(id);
    }
}
