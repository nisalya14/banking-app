package com.banking.service;

import com.banking.dao.AccountDAO;
import com.banking.dao.LoanDAO;
import com.banking.model.Account;
import com.banking.model.Loan;
import com.banking.util.DBConnection;

import java.sql.Connection;

public class LoanApprovalService {

    private LoanDAO loanDAO = new LoanDAO();
    private AccountDAO accountDAO = new AccountDAO();

    public void approveLoan(int loanId) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            Loan loan = loanDAO.getLoanById(loanId);
            if (loan == null) throw new Exception("Loan not found");
            if (!"PENDING".equalsIgnoreCase(loan.getStatus())) throw new Exception("Loan already processed");

            Account account = accountDAO.getAccountByUserId(loan.getUserId(), conn);
            if (account == null) throw new Exception("Account not found");

            // Update loan
            loanDAO.updateLoanStatus(loanId, "APPROVED", conn);

            // Update balance
            accountDAO.updateBalance(account.getAccountId(), account.getBalance() + loan.getLoanAmount(), conn);

            // Insert transaction
            accountDAO.insertTransaction(account.getAccountId(), loan.getLoanAmount(), "Loan Disbursement", conn);

            conn.commit();
        }
    }
}