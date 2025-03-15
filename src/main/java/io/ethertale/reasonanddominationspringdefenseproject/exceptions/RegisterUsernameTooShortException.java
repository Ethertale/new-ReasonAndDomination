package io.ethertale.reasonanddominationspringdefenseproject.exceptions;

public class RegisterUsernameTooShortException extends RuntimeException {

    public RegisterUsernameTooShortException() {
    }

    public RegisterUsernameTooShortException(String message) {
        super(message);
    }
}
