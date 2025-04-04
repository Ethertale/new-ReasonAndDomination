package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;

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
    @ExceptionHandler(LoginProfileDeactivatedException.class)
    public String handleLoginProfileDeactivatedException(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("loginProfileDeactivated", "Profile deactivated!");
        return "redirect:/login";
    }
    @ExceptionHandler(LoginProfileDoesNotExistException.class)
    public String handleLoginProfileDoesNotExistException(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("loginProfileDoesNotExist", "Invalid Email or Password!");
        return "redirect:/login";
    }
    @ExceptionHandler(LoginProfileWrongPasswordException.class)
    public String handleLoginProfileWrongPasswordException(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("loginProfileDeactivated", "Invalid Email or Password!");
        return "redirect:/login";
    }
    @ExceptionHandler(DungeonDungeonWithThisTitleDoesNotExist.class)
    public String handleDungeonDungeonWithThisTitleDoesNotExist(){
        return "redirect:/dungeons";
    }
    @ExceptionHandler(ForumPostContentBlankOrEmptyComment.class)
    public String handleForumPostContentBlankOrEmptyComment(ForumPostContentBlankOrEmptyComment postRedirect, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Comment cannot be blank or empty for post: " + postRedirect.getPostSlug());
        return "redirect:/posts/" + postRedirect.getPostSlug();
    }
    @ExceptionHandler(ItemDoesNotExistException.class)
    public String handleItemDoesNotExistException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("itemDoesNotExist", "Item does not exist!");
        return "redirect:/items";
    }
    @ExceptionHandler(GuideDoesNotExistException.class)
    public String handleGuideDoesNotExistException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("guideDoesNotExist", "Guide does not exist!");
        return "redirect:/guides";
    }
    @ExceptionHandler(GuideFormContentIsEmptyException.class)
    public String handleGuideFormContentIsEmptyException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("guideFormContentIsEmpty", "Content is empty!");
        return "redirect:/guides/create-guide";
    }
    @ExceptionHandler(GuideFormTitleIsEmptyException.class)
    public String handleGuideFormTitleIsEmptyException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("guideFormTitleIsEmpty", "Title is empty!");
        return "redirect:/guides/create-guide";
    }
    @ExceptionHandler(NewsDoesNotExistException.class)
    public String handleNewsDoesNotExistException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("newsDoesNotExist", "News does not exist!");
        return "redirect:/news/create-news";
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("accessDenied", "Access denied!");
        return "redirect:/home";
    }
}
