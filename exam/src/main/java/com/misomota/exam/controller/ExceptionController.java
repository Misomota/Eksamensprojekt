package com.misomota.exam.controller;

import com.misomota.exam.DRY.DatabaseOperationException;
import com.misomota.exam.DRY.DuplicateProfileException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import com.misomota.exam.DRY.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(NotFoundException ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", "Not Found");
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(DuplicateProfileException.class)
    public String handleDuplicate(DuplicateProfileException ex, Model model) {
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("error", "Duplicate Entry");
        model.addAttribute("message", ex.getMessage());
        return "error/error";
    }

    @ExceptionHandler({DatabaseOperationException.class})
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("error", "Internal Server Error");
        model.addAttribute("message", "Something went wrong. Please try again later.");
        return "error/500";
    }
}
