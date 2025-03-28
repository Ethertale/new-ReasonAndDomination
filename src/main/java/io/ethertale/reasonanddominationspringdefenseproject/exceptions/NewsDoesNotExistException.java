package io.ethertale.reasonanddominationspringdefenseproject.exceptions;

public class NewsDoesNotExistException extends RuntimeException {
    public NewsDoesNotExistException() {
    }

    public NewsDoesNotExistException(String message) {
        super(message);
    }
}
