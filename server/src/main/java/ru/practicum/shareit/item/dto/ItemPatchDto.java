package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class ItemPatchDto {
    private Long id;
    private String name; // — краткое название;
    private String description; // — развёрнутое описание;
    private Boolean available; // — статус о том, доступна или нет вещь для аренды;
}
