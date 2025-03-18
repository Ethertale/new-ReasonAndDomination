package io.ethertale.reasonanddominationspringdefenseproject.exceptions;

public class LoginProfileWrongPasswordException extends RuntimeException {
  public LoginProfileWrongPasswordException() {
  }

  public LoginProfileWrongPasswordException(String message) {
        super(message);
    }
}
