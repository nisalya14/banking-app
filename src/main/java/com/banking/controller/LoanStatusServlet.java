package com.banking.controller;

import com.banking.model.Loan;
import com.banking.service.LoanService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/loan-status")
public class LoanStatusServlet extends HttpServlet {

    private LoanService loanService = new LoanService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int loanId = Integer.parseInt(request.getParameter("loan_id"));
            Loan loan = loanService.getLoanById(loanId);
            if (loan == null) {
                out.println(gson.toJson(Map.of("error", "Loan not found")));
            } else {
                out.println(gson.toJson(loan));
            }
        } catch (Exception e) {
            out.println(gson.toJson(Map.of("error", e.getMessage())));
        }
    }
}