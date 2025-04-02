package io.ethertale.reasonanddominationspringdefenseproject.exceptions;

public class GuideDoesNotExistException extends RuntimeException {
    public GuideDoesNotExistException() {
    }

    public GuideDoesNotExistException(String message) {
        super(message);
    }
}
