package io.ethertale.reasonanddominationspringdefenseproject.exceptions;

public class RegisterInvalidConfirmPasswordException extends RuntimeException {
    public RegisterInvalidConfirmPasswordException() {
    }

    public RegisterInvalidConfirmPasswordException(String message) {
        super(message);
    }
}
