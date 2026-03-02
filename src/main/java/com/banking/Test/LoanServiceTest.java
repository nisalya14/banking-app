package com.banking.Test;

import com.banking.service.LoanService;

public class LoanServiceTest {

    public static void main(String[] args) {

        LoanService service = new LoanService();

        double emi = service.calculateEMI(50000, 8, 24);

        System.out.println("Monthly EMI: " + emi);
    }
}