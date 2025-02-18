package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ExsistingEmailException;
import ru.practicum.shareit.exception.StorageException;

import java.util.HashMap;
import java.util.Optional;

@Component
public class UserStorageInMemory implements UserStorage {
    private HashMap<Long, User> users = new HashMap<>();
    private Long counter = 0L;

    @Override
    public Optional<User> create(User user) {
        checkMail(user);
        user.setId(++counter);
        users.put(user.getId(), user);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> get(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public void save(User user) {
        checkMail(user);
        users.put(user.getId(), user);
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
    }

    private void checkMail(User newUser) {
        if (newUser.getEmail() == null || newUser.getEmail().isEmpty()) {
            throw new StorageException("Плохая почта");
        }
        if (newUser.getName() == null || newUser.getName().isEmpty()) {
            throw new StorageException("Плохое имя");
        }
        if (users.values().stream()
                .filter(u -> u.getEmail().equals(newUser.getEmail()) && !u.getId().equals(newUser.getId()))
                .findFirst()
                .isPresent()) {
            throw new ExsistingEmailException("Email already exists");
        }
    }
}
