package com.capstone.producer.exceptions;

public class RequestInvalidException extends Exception {
    public RequestInvalidException(Error error) {super(error);};
}
