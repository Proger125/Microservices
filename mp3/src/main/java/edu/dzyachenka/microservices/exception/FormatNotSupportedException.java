package edu.dzyachenka.microservices.exception;

public class FormatNotSupportedException extends RuntimeException {
    public FormatNotSupportedException(String message) {
        super(message);
    }
}
