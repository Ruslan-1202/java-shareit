package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.StorageException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserStorageImpl implements UserStorage {
//    private final HashMap<Long, User> users = new HashMap<>();
//    private Long counter = 0L;
    private final UserRepository userRepository;

    @Override
    public Optional<User> create(User user) {
       // checkMail(user);
        return Optional.ofNullable(userRepository.save(user));
    }

    @Override
    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
     //   checkMail(user);
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private void checkMail(User newUser) {
        if (newUser.getEmail() == null || newUser.getEmail().isEmpty()) {
            throw new StorageException("Плохая почта");
        }
        if (newUser.getName() == null || newUser.getName().isEmpty()) {
            throw new StorageException("Плохое имя");
        }
//        if (users.values().stream()
//                .filter(u -> u.getEmail().equals(newUser.getEmail()) && !u.getId().equals(newUser.getId()))
//                .findFirst()
//                .isPresent()) {
//            throw new ExsistingEmailException("Такая почта уже есть");
//        }
    }
}
