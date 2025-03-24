package ru.practicum.shareit.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse methodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getBindingResult().getFieldErrors().stream()
                .map(error -> (error.getField() + ": " + error.getDefaultMessage()))
                .collect(Collectors.joining("; ")));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exception(final Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }
}
