package io.ethertale.reasonanddominationspringdefenseproject.exceptions;

public class GuideFormTitleIsEmptyException extends RuntimeException {
    public GuideFormTitleIsEmptyException() {
    }

    public GuideFormTitleIsEmptyException(String message) {
        super(message);
    }
}
