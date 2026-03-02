package com.banking.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestConnection {

    public static void main(String[] args) {

        try (Connection conn = DBConnection.getConnection()) {

            String query = "SELECT account_id, transaction_type, amount FROM transactions";

            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        "Account ID: " + rs.getInt("account_id") +
                        ", Type: " + rs.getString("transaction_type") +
                        ", Amount: " + rs.getDouble("amount")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}