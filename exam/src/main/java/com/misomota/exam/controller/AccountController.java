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
@RequestMapping("/Account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/register")
    public String ShowRegisterAccount(Model model) {
        model.addAttribute("account", new Account());
        return "registerAndLogin";
    }

    @PostMapping("/register")
    public String registerAccount(@ModelAttribute Account account, Model model) {
        accountService.saveAccount(account);
        model.addAttribute("SignUpMessage", "Register succesfull");
        return "showProject";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("account", new Account());
        return "registerAndLogin";
    }

    @PostMapping("/login")
     public String loginAccount(@ModelAttribute Account account, Model model) {
        boolean valid = accountService.validateLogin(account.getUsername(), account.getAccountPassword());
        if (valid) {
            model.addAttribute("username", account.getUsername());
            return "showProject";
        } else {
            model.addAttribute("error", "invalid username or password");
            return "registerAndLogin";
        }
     }
}
