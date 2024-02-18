package com.capstone.producer.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends Exception {
    private final HttpStatus status;

    private final String message;

    public HttpException(HttpStatus status, String message) {
        super(message);

        this.status = status;
        this.message = message;
    }
}
