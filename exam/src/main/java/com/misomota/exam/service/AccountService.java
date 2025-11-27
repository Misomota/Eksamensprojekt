package com.misomota.exam.service;

import com.misomota.exam.model.Account;
import com.misomota.exam.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account saveAccount(Account account) {
        return accountRepository.saveAccount(account);
    }

    public boolean validateLogin(String username, String accountPassword) {
        return accountRepository.validateLogin(username, accountPassword);
    }
}