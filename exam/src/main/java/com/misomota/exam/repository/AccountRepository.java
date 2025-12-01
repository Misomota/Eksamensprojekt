package com.misomota.exam.repository;

import com.misomota.exam.model.Account;
import com.misomota.exam.model.Role;
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
        String sql = "INSERT INTO account (username, password, role) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getRole().name());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            account.setAccountID(key.intValue());
        }
        return account;
    }

    public Account findAccountByUsername(String username) {
        String sql = "SELECT accountID, username, password, role FROM account WHERE username = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
                Account account = new Account();
                account.setAccountID(rs.getInt("accountID"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setRole(Role.valueOf(rs.getString("role"))); // convert string to enum
                return account;
            });
    }
}