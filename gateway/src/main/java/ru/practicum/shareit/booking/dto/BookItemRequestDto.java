package ru.practicum.shareit.booking.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemRequestDto {
	@NotNull
	@Positive
	private Long itemId;
	@NotNull(message = "задайте дату начала")
	@FutureOrPresent
	private LocalDateTime start;
	@NotNull
	@Future(message = "дата окончания не может быть в прошлом")
	private LocalDateTime end;

	@AssertTrue(message = "дата начала должна быть меньше конца")
	public boolean isDatesValid() { //метод обязательно начинается с "is"
		if (start == null || end == null) {
			return true;
		}
		return start.isBefore(end);
	}
}
