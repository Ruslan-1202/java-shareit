package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingCreateDto {
    @NotNull
    @Positive
    private Long itemId;
    @NotNull(message = "задайте дату начала")
    private LocalDateTime start;
    @NotNull
    @FutureOrPresent(message = "дата окончания не может быть в прошлом")
    private LocalDateTime end;

    @AssertTrue(message = "дата начала должна быть меньше конца")
    public boolean isDatesValid() { //метод обязательно начинается с "is"
        if (start == null || end == null) {
            return true;
        }
        return start.isBefore(end);
    }
}
