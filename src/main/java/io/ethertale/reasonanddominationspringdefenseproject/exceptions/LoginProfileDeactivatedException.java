package io.ethertale.reasonanddominationspringdefenseproject.exceptions;

public class LoginProfileDeactivatedException extends RuntimeException {
    public LoginProfileDeactivatedException() {
    }

    public LoginProfileDeactivatedException(String message) {
        super(message);
    }
}
