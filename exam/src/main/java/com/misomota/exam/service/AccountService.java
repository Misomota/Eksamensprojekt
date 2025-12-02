package com.misomota.exam.service;

import com.misomota.exam.model.Account;
import com.misomota.exam.model.Role;
import com.misomota.exam.repository.AccountRepository;
import org.springframework.stereotype.Service;


@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account saveAccount(Account account) {
        if (account.getRole() == null) {
            account.setRole(Role.USER);
        }
        return accountRepository.saveAccount(account);
    }

    public Account findAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    public boolean login(String username, String pw) {
        Account account = accountRepository.findAccountByUsername(username);
        if (account != null) {
            return account.getPassword().equals(pw);
        }
            return false;
    }
}