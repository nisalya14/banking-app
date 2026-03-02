package com.banking.controller;

import com.banking.service.LoanApprovalService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/approve-loan")
public class ApproveLoanServlet extends HttpServlet {

    private LoanApprovalService approvalService = new LoanApprovalService();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("role"))) {
            out.println(gson.toJson(Map.of("error", "Unauthorized - Admin Only")));
            return;
        }

        try {
            int loanId = Integer.parseInt(request.getParameter("loan_id"));
            approvalService.approveLoan(loanId);
            out.println(gson.toJson(Map.of("message", "Loan approved successfully")));
        } catch (Exception e) {
            out.println(gson.toJson(Map.of("error", e.getMessage())));
        }
    }
}