package com.banking.controller;

import com.banking.service.UserService;
import com.banking.util.ValidationUtil;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private Gson gson = new Gson();
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, String> result = new HashMap<>();

        try {

            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            if (!ValidationUtil.isValidName(name)) {
                throw new IllegalArgumentException("Invalid Name");
            }

            if (!ValidationUtil.isValidEmail(email)) {
                throw new IllegalArgumentException("Invalid Email");
            }

            if (!ValidationUtil.isValidPassword(password)) {
                throw new IllegalArgumentException(
                        "Password must contain 8 characters, 1 uppercase, 1 lowercase, 1 digit");
            }

            if (role == null || role.trim().isEmpty()) {
                role = "USER";
            }

            // ✅ Call Service Layer
            userService.register(name, email, password, role);

            response.setStatus(HttpServletResponse.SC_CREATED);
            result.put("message", "User Registered Successfully");

        } catch (IllegalArgumentException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            result.put("error", e.getMessage());

        } catch (Exception e) {

            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.put("error", "Registration Failed");
        }

        out.println(gson.toJson(result));
    }
}