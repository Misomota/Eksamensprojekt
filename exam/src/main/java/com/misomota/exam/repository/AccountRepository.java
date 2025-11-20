package com.misomota.exam.repository;

import com.misomota.exam.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Account saveAccount(Account account) {
        String sql = "INSERT INTO account (username, password) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conection -> {
            PreparedStatement ps = conection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(2, account.getUsername());
            ps.setString(1, account.getPassword());
            return ps;
        });
        return account;
    }

    public boolean validateLogin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM account WHERE username = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        return count != null && count > 0;
    }
}