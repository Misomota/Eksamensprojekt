package com.misomota.exam.controller;

import com.misomota.exam.model.Account;
import com.misomota.exam.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("login", new Account());
        return "index";
    }

    @GetMapping("/register")
    public String ShowRegisterAccount(Model model) {
        model.addAttribute("register", new Account());
        return "register";
    }

    @PostMapping("/register")
    public String registerAccount(@ModelAttribute Account account) {
        accountService.saveAccount(account);
        return "redirect:/account/login";
    }
}
