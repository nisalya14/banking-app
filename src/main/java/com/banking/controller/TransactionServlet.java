package com.banking.controller;

import com.banking.model.Transaction;
import com.banking.service.TransactionService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {

    private TransactionService service = new TransactionService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
    	 HttpSession session = request.getSession(false);
    	System.out.println("Session: " + session);
    	System.out.println("Role: " + (session != null ? session.getAttribute("role") : null));

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
        	
            //  Check Session
          

            if (session == null ||
                !"ADMIN".equalsIgnoreCase((String) session.getAttribute("role"))) {

                out.println(gson.toJson(
                        Map.of("error", "Unauthorized - Admin Only")
                ));
                return;
            }

            //  Validate Required Params
            String accountIdParam = request.getParameter("account_id");
            String typeParam = request.getParameter("transaction_type");

            if (accountIdParam == null || typeParam == null) {
                out.println(gson.toJson(
                        Map.of("error", "account_id and transaction_type are required")
                ));
                return;
            }

            int accountId = Integer.parseInt(accountIdParam);

            // Pagination Defaults
            int page = request.getParameter("page") == null
                    ? 1
                    : Integer.parseInt(request.getParameter("page"));

            int size = request.getParameter("size") == null
                    ? 5
                    : Integer.parseInt(request.getParameter("size"));

            if (page <= 0 || size <= 0) {
                out.println(gson.toJson(
                        Map.of("error", "page and size must be greater than 0")
                ));
                return;
            }

            // Call Service
            List<Transaction> transactions =
                    service.fetchTransactions(accountId, typeParam, page, size);

            // Return Success Response
            out.println(gson.toJson(
                    Map.of(
                            "page", page,
                            "size", size,
                            "data", transactions
                    )
            ));

        } catch (NumberFormatException e) {

            out.println(gson.toJson(
                    Map.of("error", "Invalid numeric input")
            ));

        } catch (Exception e) {

            out.println(gson.toJson(
                    Map.of("error", e.getMessage())
            ));
        }
        
    }
    
}