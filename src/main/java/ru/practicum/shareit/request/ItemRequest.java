package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Entity
@Table(name = "requests", schema = "public")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //— уникальный идентификатор запроса;
    private String description; //— текст запроса, содержащий описание требуемой вещи;
    @ManyToOne(fetch = FetchType.LAZY)
    private User requestor; //— пользователь, создавший запрос;
    private LocalDateTime created; //— дата и время создания запроса.
}
