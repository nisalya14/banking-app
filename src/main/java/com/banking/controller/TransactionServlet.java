package com.banking.controller;

import com.banking.service.TransactionService;

import jakarta.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.getWriter().println("Login required!");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            response.getWriter().println("Access Denied: Admins only");
            return;
        }

        try {

            String accountIdParam = request.getParameter("account_id");
            String typeParam = request.getParameter("transaction_type");

            if (accountIdParam == null || typeParam == null) {
                response.getWriter().println("Please provide account_id and transaction_type");
                return;
            }

            int accountId = Integer.parseInt(accountIdParam);

            TransactionService service = new TransactionService();
            List<String> transactions = service.fetchTransactions(accountId, typeParam);

            if (transactions.isEmpty()) {
                response.getWriter().println("No transactions found");
            } else {
                for (String t : transactions) {
                    response.getWriter().println(t);
                }
            }

        } catch (Exception e) {
            response.getWriter().println("Error fetching transactions");
        }
    }
}