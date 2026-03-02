package com.banking.service;

import com.banking.dao.LoanDAO;
import com.banking.model.Loan;

public class LoanService {

    private LoanDAO loanDAO = new LoanDAO();

    // Calculate EMI
    public double calculateEMI(double principal, double annualRate, int tenureMonths) {
        if (annualRate == 0) return principal / tenureMonths;
        double monthlyRate = annualRate / (12 * 100);
        double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths))
                     / (Math.pow(1 + monthlyRate, tenureMonths) - 1);
        return Math.round(emi * 100.0) / 100.0;
    }

    public void applyLoan(int userId, double loanAmount, double interestRate, int tenureMonths) throws Exception {
        loanDAO.applyLoan(userId, loanAmount, interestRate, tenureMonths);
    }

    public Loan getLoanById(int loanId) throws Exception {
        return loanDAO.getLoanById(loanId);
    }
}