package com.schoolmanagement.poc.exception;

import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class Handlers {

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(BAD_REQUEST)
    public StandardError    responseStatusError(ResponseStatusException e, HttpServletRequest request) {
        return StandardError.builder()
                .timestamp(Instant.now())
                .error(List.of(StandardErrorObject.builder()
                        .status(e.getStatus().value())
                        .errorMessage("Ocorreu um problema.")
                        .message(e.getReason())
                        .path(request.getRequestURI())
                        .build()))
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public StandardError badRequestError(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        return (StandardError.builder()
                .timestamp(Instant.now())
                .error(fieldErrorList.stream().map(fieldError -> StandardErrorObject.builder()
                        .errorMessage("Requisição não foi feita corretamente. Verifique!")
                        .message(fieldError.getDefaultMessage())
                        .status(BAD_REQUEST.value())
                        .path(request.getRequestURI())
                        .build()).toList())
                .build());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public StandardError methodNotAllowedError(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        return StandardError.builder()
                .timestamp(Instant.now())
                .error(List.of(StandardErrorObject.builder()
                        .status(METHOD_NOT_ALLOWED.value())
                        .errorMessage("O método chamado não pode ser requisitado nessa url. Verifique!")
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                        .build()))
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public StandardError illegalArgumentError (IllegalArgumentException e, HttpServletRequest request) {
        return StandardError.builder()
                .timestamp(Instant.now())
                .error(List.of(StandardErrorObject.builder()
                        .status(BAD_REQUEST.value())
                        .errorMessage("Argumentos inválidos, confira sua requisição.")
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                        .build()))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public StandardError defaultError(Exception e, HttpServletRequest request) {
        return StandardError.builder()
                .timestamp(Instant.now())
                .error(List.of(StandardErrorObject.builder()
                        .errorMessage("Erro interno.")
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                        .build()))
                .build();
    }
}