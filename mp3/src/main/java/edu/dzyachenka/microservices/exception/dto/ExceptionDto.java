package edu.dzyachenka.microservices.exception.dto;

public class ExceptionDto {
    private String message;

    public ExceptionDto(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
