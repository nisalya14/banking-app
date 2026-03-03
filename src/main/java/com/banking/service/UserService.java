package com.banking.service;

import com.banking.dao.UserDAO;
import com.banking.model.User;
import com.banking.util.DBConnection;
import com.banking.util.PasswordUtil;

import java.sql.Connection;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    // Register new user (hashes password before storing)
    public void register(String name, String email, String password, String role) throws Exception {

        if (name == null || email == null || password == null || role == null) {
            throw new Exception("All fields are required");
        }

        try (Connection conn = DBConnection.getConnection()) {
            String hashedPassword = PasswordUtil.hashPassword(password);
            userDAO.registerUser(conn, name, email, hashedPassword, role);
        }
    }

    // Login user
    public User login(String email, String password) throws Exception {

        if (email == null || password == null) {
            throw new Exception("Email and Password required");
        }

        try (Connection conn = DBConnection.getConnection()) {
            User user = userDAO.getUserByEmail(conn, email);

            if (user == null) {
                throw new Exception("User not found");
            }

            // ✅ Correct check with hashed password
            if (!PasswordUtil.checkPassword(password, user.getPassword())) {
                throw new Exception("Incorrect password");
            }

            return user;
        }
    }
}