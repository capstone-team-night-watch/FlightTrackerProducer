package com.capstone.producer.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 * Http exception to be thrown on error
 */
@Getter
public class HttpException extends Exception {
    private final HttpStatus status;

    private final String message;

    /**
     * HttpException constructor
     *
     * @param status the status response to be set
     * @param message The text message to be set
     */
    public HttpException(HttpStatus status, String message) {
        super(message);

        this.status = status;
        this.message = message;
    }
}
