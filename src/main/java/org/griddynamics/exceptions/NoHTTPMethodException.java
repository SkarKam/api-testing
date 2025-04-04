package org.griddynamics.exceptions;

public class NoHTTPMethodException extends RuntimeException{
    public NoHTTPMethodException(String message) {
        super(message);
    }
}
