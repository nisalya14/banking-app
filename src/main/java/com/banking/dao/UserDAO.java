package com.banking.dao;

import com.banking.model.User;
import com.banking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // Insert user into DB
    public void registerUser(Connection conn, String name, String email, String password, String role) throws Exception {

        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, password);
        ps.setString(4, role);

        ps.executeUpdate();
    }

    // Fetch user by email
    public User getUserByEmail(Connection conn, String email) throws Exception {

        String sql = "SELECT * FROM users WHERE email = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            return user;
        }

        return null;
    }
}