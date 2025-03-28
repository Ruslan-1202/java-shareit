package ru.practicum.shareit.exception;

public class ExsistingEmailException extends RuntimeException {
    public ExsistingEmailException(String message) {
        super(message);
    }
}
