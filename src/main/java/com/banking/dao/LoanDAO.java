package com.banking.dao;

import com.banking.model.Loan;
import com.banking.util.DBConnection;

import java.sql.*;

public class LoanDAO {

    // Apply a new loan
    public void applyLoan(int userId, double loanAmount, double interestRate, int tenureMonths) throws Exception {
        String sql = "INSERT INTO loans(user_id, loan_amount, interest_rate, tenure_months, status, created_at) " +
                     "VALUES (?, ?, ?, ?, 'PENDING', CURRENT_TIMESTAMP)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setDouble(2, loanAmount);
            ps.setDouble(3, interestRate);
            ps.setInt(4, tenureMonths);
            ps.executeUpdate();
        }
    }

    // Fetch loan by ID
    public Loan getLoanById(int loanId) throws Exception {
        String sql = "SELECT * FROM loans WHERE loan_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Loan loan = new Loan();
                loan.setLoanId(rs.getInt("loan_id"));
                loan.setUserId(rs.getInt("user_id"));
                loan.setLoanAmount(rs.getDouble("loan_amount"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setTenureMonths(rs.getInt("tenure_months"));
                loan.setStatus(rs.getString("status"));
                return loan;
            }
            return null;
        }
    }

    // Update loan status
    public void updateLoanStatus(int loanId, String status, Connection conn) throws SQLException {
        String sql = "UPDATE loans SET status = ? WHERE loan_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, loanId);
            ps.executeUpdate();
        }
    }
}