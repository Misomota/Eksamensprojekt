package com.misomota.exam.service;

import com.misomota.exam.DRY.DatabaseOperationException;
import com.misomota.exam.DRY.DuplicateProfileException;
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

    public Account saveAccount(Account account) {
        try {
            Account existing = accountRepository.findAccountByUsername(account.getUsername());
            if (existing != null) {
                throw new DuplicateProfileException("Account already exists");
            }
            if (account.getRole() == null) {
                account.setRole(Role.USER);
            }
            return accountRepository.saveAccount(account);

        } catch (Exception e) {
            throw new DatabaseOperationException("Error while saving account", e);
        }
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
            if (account == null) {
                throw new NotFoundException("Account not found");
            }
            if (!account.getPassword().equals(pw)) {
                return false;
            }
            return true;
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("Couldn´t retireve account during login", dataAccessException);
        }
    }
}