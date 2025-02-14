package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * TODO Sprint add-controllers.
 */
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id; // — уникальный идентификатор пользователя;
    private String name; // — имя или логин пользователя;
    private String email; // — адрес электронной почты (учтите, что д
}
