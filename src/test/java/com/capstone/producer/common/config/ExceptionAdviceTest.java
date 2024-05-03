package com.capstone.producer.common.config;

import com.capstone.producer.exceptions.RequestInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class ExceptionAdviceTest {

    private ExceptionAdvice exceptionAdvice;

    @BeforeEach
    public void setUp() {
        exceptionAdvice = new ExceptionAdvice();
    }

    @Test
    void handle_request_invalid_exception_should_return_message() {
        String errorMessage = "Invalid request";
        RequestInvalidException request = new RequestInvalidException(new Error(errorMessage));

        Object result = exceptionAdvice.handleRequestInvalidException(request);

        assertNotNull(result);
    }

}