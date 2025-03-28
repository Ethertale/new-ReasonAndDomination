package io.ethertale.reasonanddominationspringdefenseproject.exceptions;

public class GuideFormContentIsEmptyException extends RuntimeException {
    public GuideFormContentIsEmptyException() {
    }

    public GuideFormContentIsEmptyException(String message) {
        super(message);
    }
}
