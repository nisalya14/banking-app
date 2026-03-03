package com.banking.controller;

import com.banking.model.User;
import com.banking.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(email == null || password == null || email.isEmpty() || password.isEmpty()) {
            out.write("{\"error\":\"Email and password required\"}");
            return;
        }

        try {
            UserService service = new UserService();
            User user = service.login(email, password);

            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
            session.setAttribute("role", user.getRole());

            out.write("{\"success\":\"Login successful\"}");

        } catch (Exception e) {
            out.write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}