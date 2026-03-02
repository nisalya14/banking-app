package com.banking.controller;

import com.banking.service.LoanService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loan")
public class LoanServlet extends HttpServlet {

    private LoanService loanService = new LoanService();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int userId = Integer.parseInt(request.getParameter("user_id"));
            double loanAmount = Double.parseDouble(request.getParameter("loan_amount"));
            double interestRate = Double.parseDouble(request.getParameter("interest_rate"));
            int tenureMonths = Integer.parseInt(request.getParameter("tenure_months"));

            double emi = loanService.calculateEMI(loanAmount, interestRate, tenureMonths);
            loanService.applyLoan(userId, loanAmount, interestRate, tenureMonths);
// map holds key-value pairs (message, EMI, status).
            Map<String, Object> res = new HashMap<>();
            res.put("message", "Loan applied successfully");
            res.put("EMI", emi);
            res.put("status", "PENDING");
            out.println(gson.toJson(res));

        } catch (Exception e) {
            out.println(gson.toJson(Map.of("error", e.getMessage())));
        }
    }
}