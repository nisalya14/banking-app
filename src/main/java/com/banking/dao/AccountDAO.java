package com.banking.dao;

import com.banking.model.Account;
import com.banking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    public Account getAccountByUserId(int userId, Connection conn) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Account account = new Account();
            account.setAccountId(rs.getInt("account_id"));
            account.setUserId(rs.getInt("user_id"));
            account.setBalance(rs.getDouble("balance"));
            return account;
        }
        return null;
    }

    public void updateBalance(int accountId, double newBalance, Connection conn) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        }
    }

    public void insertTransaction(int accountId, double amount, String description, Connection conn) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, transaction_type, amount, transaction_date, description) " +
                     "VALUES (?, 'CREDIT', ?, NOW(), ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.setDouble(2, amount);
            ps.setString(3, description);
            ps.executeUpdate();
        }
    }
}