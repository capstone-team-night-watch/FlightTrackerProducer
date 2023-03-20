package com.capstone.producer.config;

import com.capstone.producer.exceptions.RequestInvalidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    public ExceptionAdvice() {}

    @ExceptionHandler(RequestInvalidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Object handleRequestInvalidException (
            final RequestInvalidException ex,
            HttpServletRequest servletRequest
    ) {
        LOGGER.warn("handleRequestInvalidException: {}", ex.getMessage());
        return ex.getMessage();
    }
}
