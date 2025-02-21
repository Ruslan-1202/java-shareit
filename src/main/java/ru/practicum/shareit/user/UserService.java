package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.StorageException;
import ru.practicum.shareit.user.dto.UserDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public UserDto create(UserDto userDto) {
        User user = userStorage.create(UserMapper.toUser(userDto))
                .orElseThrow(() -> new StorageException("Не удалось создать юзера"));
        return UserMapper.toUserDto(user);
    }

    public UserDto get(Long id) {
        return UserMapper.toUserDto(userStorage.get(id)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + id + " не найден")));
    }

    public UserDto save(Long id, UserDto userDto) {
        User user = userStorage.get(id)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + id + " не найден"));
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        userStorage.save(user);
        return UserMapper.toUserDto(user);
    }

    public void delete(Long id) {
        userStorage.delete(id);
    }
}
