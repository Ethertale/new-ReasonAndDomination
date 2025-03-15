package io.ethertale.reasonanddominationspringdefenseproject.exceptions;

public class RegisterInvalidEmailException extends RuntimeException {
    public RegisterInvalidEmailException() {
    }

    public RegisterInvalidEmailException(String message) {
        super(message);
    }
}
