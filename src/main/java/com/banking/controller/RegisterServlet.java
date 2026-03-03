package com.banking.controller;

import com.banking.dao.UserDAO;
import com.banking.util.DBConnection;
import com.banking.util.EmailUtil;
import com.banking.util.ValidationUtil;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, String> result = new HashMap<>();

        try {
            // 1️⃣ Get Parameters
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            // 2️⃣ Input Validation
            if (!ValidationUtil.isValidName(name)) {
                result.put("error", "Invalid Name");
                out.println(gson.toJson(result));
                return;
            }

            if (!ValidationUtil.isValidEmail(email)) {
                result.put("error", "Invalid Email");
                out.println(gson.toJson(result));
                return;
            }

            if (!ValidationUtil.isValidPassword(password)) {
                result.put("error",
                        "Password must contain 8 characters, 1 uppercase, 1 lowercase, 1 digit");
                out.println(gson.toJson(result));
                return;
            }

            if (role == null || role.isEmpty()) {
                role = "USER";  // default role
            }

            // 3️⃣ Hash Password (SHA-256)
            String hashedPassword = hashPassword(password);

            // 4️⃣ Save to Database
            UserDAO dao = new UserDAO();
            try (Connection conn = DBConnection.getConnection()) {
                dao.registerUser(conn, name, email, hashedPassword, role);
            }

            // 5️⃣ Send Email Notification
        //    EmailUtil.sendEmail(email, name);

            result.put("message", "User Registered Successfully");
            out.println(gson.toJson(result));

        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "Registration Failed");
            out.println(gson.toJson(result));
        }
    }

    // 🔐 Password Hashing Method
    private String hashPassword(String password) throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}