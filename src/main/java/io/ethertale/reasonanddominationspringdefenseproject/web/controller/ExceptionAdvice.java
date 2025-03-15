package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.exceptions.RegisterInvalidConfirmPasswordException;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.RegisterInvalidEmailException;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.RegisterPasswordTooShortException;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.RegisterUsernameTooShortException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(RegisterUsernameTooShortException.class)
    public String handleRegisterUsernameTooShortException(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("usernameTooShort", "Username must be between 5 and 15 characters!");
        return "redirect:/register";
    }
    @ExceptionHandler(RegisterPasswordTooShortException.class)
    public String handleRegisterPasswordTooShortException(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("passwordTooShort", "Password must be at least 6 characters!");
        return "redirect:/register";
    }
    @ExceptionHandler(RegisterInvalidEmailException.class)
    public String handleRegisterInvalidEmailException(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("invalidEmail", "Invalid Email format!");
        return "redirect:/register";
    }
    @ExceptionHandler(RegisterInvalidConfirmPasswordException.class)
    public String handleRegisterInvalidConfirmPasswordException(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("invalidConfirmPassword", "Password is not the same!");
        return "redirect:/register";
    }
}
