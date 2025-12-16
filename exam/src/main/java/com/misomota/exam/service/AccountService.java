package com.misomota.exam.service;

import com.misomota.exam.DRY.DatabaseOperationException;
import com.misomota.exam.DRY.NotFoundException;
import com.misomota.exam.model.Account;
import com.misomota.exam.model.Role;
import com.misomota.exam.repository.AccountRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String saveAccount(Account account) {
            if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
                return "Username cannot be empty";
            }
            if (account.getPassword() == null || account.getPassword().trim().isEmpty()) {
                return "Password cannot be empty";
            }
            if (account.getPassword().length() > 100) {
                return "Password cannot be more than 100 characters";
            }
            if (account.getUsername().length() > 100) {
                return "USername cannot be more than 100 characters";
            }
            Account existing = accountRepository.findAccountByUsername(account.getUsername());
            if (existing != null) {
                return "Account already exists";
            }
            if (account.getRole() == null) {
                account.setRole(Role.USER);
            }
            accountRepository.saveAccount(account);
            return null;
    }

    public Account findAccountByUsername(String username) {
        try {
            Account account = accountRepository.findAccountByUsername(username);
            if (account == null) {
                throw new NotFoundException("Account not found");
            }
            return account;
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("Couldn´t retrieve account", dataAccessException);
        }
    }

    public boolean login(String username, String pw) {
        try {
            Account account = accountRepository.findAccountByUsername(username);
            if (account == null || !account.getPassword().equals(pw)) {
                return false;
            }
            return true;
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("Couldn´t retireve account during login", dataAccessException);
        }
    }
}