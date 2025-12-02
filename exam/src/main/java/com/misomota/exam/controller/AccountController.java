package com.misomota.exam.controller;

import com.misomota.exam.model.Account;
import com.misomota.exam.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("account") != null;
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            return "redirect:/OnTheDot/projects";
        }
        model.addAttribute("login", new Account());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String uid, @RequestParam("password") String pw,
                        HttpSession session,
                        Model model) {

        if (accountService.login(uid, pw)) {
            Account account = accountService.findAccountByUsername(uid);
            session.setAttribute("account", account);
            session.setMaxInactiveInterval(30 * 60);
            return "redirect:/OnTheDot/projects";
        }
        model.addAttribute("wrongCredentials", true);
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterAccount(Model model) {
        model.addAttribute("register", new Account());
        return "register";
    }

    @PostMapping("/register")
    public String registerAccount(@ModelAttribute Account account) {
        accountService.saveAccount(account);
        return "redirect:/account/login";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
