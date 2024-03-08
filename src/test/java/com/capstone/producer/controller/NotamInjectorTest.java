package com.capstone.producer.controller;

import com.capstone.producer.TfrHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotamInjectorTest {

    @Mock
    private TfrHandler tfrHandler;

    @InjectMocks
    private NotamInjector notamInjector;

    @Test
    public void shouldCallTfrInjectionService() throws Exception {
        when(tfrHandler.handleTfrAddition(isA(String.class))).thenReturn("RESULT");

        ResponseEntity<String> result = notamInjector.postTFR("STRING");

        assertEquals(new ResponseEntity<>("RESULT", HttpStatus.OK), result);
    }

    @Test
    public void shouldCatchTheError() throws Exception {
        when(tfrHandler.handleTfrAddition(isA(String.class))).thenThrow(JsonProcessingException.class);

        ResponseEntity<String> result = notamInjector.postTFR("STRING");

        assertEquals(new ResponseEntity<>("Unknown processing error occured", HttpStatus.UNPROCESSABLE_ENTITY), result);
    }
}
