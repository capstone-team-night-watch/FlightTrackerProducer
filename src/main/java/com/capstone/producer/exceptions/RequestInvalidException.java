package com.capstone.producer.exceptions;

/**
 * An exception that can be thrown when a received request is invalid. Not sure if actually in use right now
 */
public class RequestInvalidException extends Exception {
    public RequestInvalidException(Error error) {super(error);}
}

