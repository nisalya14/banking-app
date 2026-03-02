package com.banking.controller;

import com.banking.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        try {
            UserService service = new UserService();
            service.register(name, email, password, role);

            response.getWriter().println("User Registered Successfully!");

        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}