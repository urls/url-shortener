package me.amarpandey.urlshortener.controller;

import me.amarpandey.urlshortener.controller.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<Response> handleClientError(HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(new Response(e.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> handleAllException(Exception e) {
        return ResponseEntity.status(BAD_REQUEST).body(new Response(e.getMessage()));
    }

    @ExceptionHandler({HttpMediaTypeException.class})
    public ResponseEntity<Response> handleMediaTypeException(HttpMediaTypeException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new Response(e.getMessage()));
    }

}
