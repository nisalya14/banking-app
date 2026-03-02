package com.banking.service;

import com.banking.dao.UserDAO;
import com.banking.model.User;
import com.banking.util.DBConnection;
import com.banking.util.PasswordUtil;

import java.sql.Connection;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    // Register logic
    public void register(String name, String email, String password, String role) throws Exception {

        if (name == null || email == null || password == null || role == null) {
            throw new Exception("All fields are required");
        }

        Connection conn = DBConnection.getConnection();

        try {
            String hashedPassword = PasswordUtil.hashPassword(password);

            userDAO.registerUser(conn, name, email, hashedPassword, role);

        } finally {
            conn.close();
        }
    }

    // Login logic
    public User login(String email, String password) throws Exception {

        if (email == null || password == null) {
            throw new Exception("Email and Password required");
        }

        Connection conn = DBConnection.getConnection();

        try {
            User user = userDAO.getUserByEmail(conn, email);

            if (user == null) {
                throw new Exception("User Not Found");
            }

            String hashedInput = PasswordUtil.hashPassword(password);

            if (!user.getPassword().equals(hashedInput)) {
                throw new Exception("Incorrect Password");
            }

            return user;

        } finally {
            conn.close();
        }
    }
}