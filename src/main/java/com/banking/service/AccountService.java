package com.banking.service;

import com.banking.dao.AccountDAO;
import com.banking.model.Account;
import com.banking.util.DBConnection;

import java.sql.Connection;

public class AccountService {

    private AccountDAO accountDAO = new AccountDAO();

    // Deposit money into account
    public void deposit(int accountId, double amount) throws Exception {
        if (amount <= 0) throw new Exception("Deposit amount must be greater than zero");

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // start transaction

            Account account = accountDAO.getAccountByUserId(accountId, conn);
            if (account == null) throw new Exception("Account not found");

            double newBalance = account.getBalance() + amount;
            accountDAO.updateBalance(accountId, newBalance, conn);
            accountDAO.insertTransaction(accountId, amount, "DEPOSIT", conn);

            conn.commit(); // commit transaction
        } catch (Exception e) {
            throw new Exception("Deposit failed: " + e.getMessage());
        }
    }

    // Withdraw money from account
    public void withdraw(int accountId, double amount) throws Exception {
        if (amount <= 0) throw new Exception("Withdrawal amount must be greater than zero");

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // start transaction

            Account account = accountDAO.getAccountByUserId(accountId, conn);
            if (account == null) throw new Exception("Account not found");

            if (account.getBalance() < amount)
                throw new Exception("Insufficient balance");

            double newBalance = account.getBalance() - amount;
            accountDAO.updateBalance(accountId, newBalance, conn);
            accountDAO.insertTransaction(accountId, amount, "WITHDRAWAL", conn);

            conn.commit(); // commit transaction
        } catch (Exception e) {
            throw new Exception("Withdrawal failed: " + e.getMessage());
        }
    }
}