package com.banking.controller;

import com.banking.util.DBConnection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/reject-loan")
public class RejectLoanServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        if (session == null || 
        	    session.getAttribute("role") == null || 
        	    !"ADMIN".equalsIgnoreCase(session.getAttribute("role").toString())) {
        	    
        	    out.println("{\"error\":\"Unauthorized - Admin Only\"}");
        	    return;
        	}

        String loanIdParam = request.getParameter("loan_id");

        if (loanIdParam == null || loanIdParam.isEmpty()) {
            out.println("{\"error\":\"Loan ID is required\"}");
            return;
        }

        try {

            int loanId = Integer.parseInt(loanIdParam);

            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT status FROM loans WHERE loan_id=?");
            ps.setInt(1, loanId);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                out.println("{\"error\":\"Loan Not Found\"}");
                return;
            }

            String status = rs.getString("status");

            if (!"PENDING".equalsIgnoreCase(status)) {
                out.println("{\"error\":\"Loan already processed\"}");
                return;
            }

            PreparedStatement update = conn.prepareStatement(
                    "UPDATE loans SET status='REJECTED' WHERE loan_id=?");
            update.setInt(1, loanId);
            update.executeUpdate();

            out.println("{\"message\":\"Loan Rejected Successfully\"}");

            conn.close();

        } catch (Exception e) {
            out.println("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}