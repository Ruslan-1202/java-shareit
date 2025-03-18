package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private Long id;
    private String description;
    private User requestor;
    private LocalDateTime created;
}
