package ru.practicum.shareit.user;

import java.util.Optional;

public interface UserStorage {
    Optional<User> create(User user);

    Optional<User> get(Long id);

    void save(User user);

    void delete(Long id);
}
