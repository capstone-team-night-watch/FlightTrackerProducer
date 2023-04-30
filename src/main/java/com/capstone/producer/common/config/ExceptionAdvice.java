package com.capstone.producer.common.config;

import com.capstone.producer.exceptions.RequestInvalidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Sets up how some exceptions are handled when thrown
 */
@ControllerAdvice
public class ExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    public ExceptionAdvice() {}

    /**
     * Adds a handler for RequestInvalidExceptions. Allows for more complex exception handling if needed
     * @param ex Exception being thrown
     * @return The exception message
     */
    @ExceptionHandler(RequestInvalidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Object handleRequestInvalidException (
            final RequestInvalidException ex
    ) {
        LOGGER.warn("handleRequestInvalidException: {}", ex.getMessage());
        // Just simply return the exception's message. Could be customized further
        return ex.getMessage();
    }
}
