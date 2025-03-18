package io.ethertale.reasonanddominationspringdefenseproject.exceptions;

public class LoginProfileDoesNotExistException extends RuntimeException {

    public LoginProfileDoesNotExistException() {
    }

    public LoginProfileDoesNotExistException(String message) {
        super(message);
    }
}
