package io.ethertale.reasonanddominationspringdefenseproject.exceptions;

public class RegisterPasswordTooShortException extends RuntimeException {

    public RegisterPasswordTooShortException() {
    }

    public RegisterPasswordTooShortException(String message) {
        super(message);
    }
}
