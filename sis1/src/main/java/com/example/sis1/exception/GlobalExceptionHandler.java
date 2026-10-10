package com.example.sis1.exception;

import com.example.sis1.exception.ApiError;
import com.example.sis1.exception.MangaNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MangaNotFoundException.class)
    public ResponseEntity<Object> handleMangaNotFound(MangaNotFoundException exception, WebRequest request) {
        ApiError body = buildError(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request,
                Map.of()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, List<String>> fieldErrors = new LinkedHashMap<>();

        for (var error : exception.getBindingResult().getFieldErrors()) {
            fieldErrors.computeIfAbsent(error.getField(),key -> new ArrayList<>()).add(error.getDefaultMessage());
        }

        ApiError body = buildError(status, "Request validation failed", request, fieldErrors);
        return handleExceptionInternal(exception, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> createResponseEntity(Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (!(body instanceof ApiError)) {
            body = buildError(
                    status,
                    "HTTP request could not be processed",
                    request,
                    Map.of()
            );
        }

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(
            Exception exception,
            WebRequest request
    ) {
        log.error("Unexpected request failure", exception);

        ApiError body = buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected server error occurred",
                request,
                Map.of()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private ApiError buildError(
            HttpStatusCode status,
            String message,
            WebRequest request,
            Map<String, List<String>> fieldErrors
    ) {
        HttpStatus httpStatus = HttpStatus.resolve(status.value());

        String error = httpStatus != null
                ? httpStatus.getReasonPhrase()
                : "HTTP error";

        String path = request instanceof ServletWebRequest servletRequest
                ? servletRequest.getRequest().getRequestURI()
                : "";

        return new ApiError(
                Instant.now(),
                status.value(),
                error,
                message,
                path,
                fieldErrors
        );
    }
}