package ru.practicum.shareit.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

@Entity
@Table(name = "items", schema = "public")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // — уникальный идентификатор вещи;
    private String name; // — краткое название;
    private String description; // — развёрнутое описание;
    @Column(name = "is_available", nullable = false)
    private boolean available; // — статус о том, доступна или нет вещь для аренды;
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner; // — владелец вещи;
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemRequest request; //— если вещь была создана по запросу другого пользователя, то в этом поле будет храниться ссылка на соответствующий запрос.
}
