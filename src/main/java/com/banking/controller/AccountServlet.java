package com.banking.controller;

import com.banking.service.AccountService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    private AccountService accountService = new AccountService();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null) {
            out.println(gson.toJson(Map.of("error", "Please login first")));
            return;
        }

        String action = request.getParameter("action");
        int accountId = Integer.parseInt(request.getParameter("account_id"));
        double amount = Double.parseDouble(request.getParameter("amount"));

        try {
            if ("deposit".equalsIgnoreCase(action)) {
                accountService.deposit(accountId, amount);
                out.println(gson.toJson(Map.of("message", "Deposit successful")));
            } else if ("withdraw".equalsIgnoreCase(action)) {
                accountService.withdraw(accountId, amount);
                out.println(gson.toJson(Map.of("message", "Withdrawal successful")));
            } else {
                out.println(gson.toJson(Map.of("error", "Invalid action")));
            }
        } catch (Exception e) {
            out.println(gson.toJson(Map.of("error", e.getMessage())));
        }
    }
}