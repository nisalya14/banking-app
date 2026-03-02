package com.banking.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public List<String> getTransactions(Connection conn, int accountId, String type) throws Exception {

        String sql = "SELECT account_id, transaction_type, amount FROM transactions WHERE account_id=? AND transaction_type=?";
        List<String> transactions = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ps.setString(2, type);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String record = "Account ID: " + rs.getInt("account_id") +
                        ", Type: " + rs.getString("transaction_type") +
                        ", Amount: " + rs.getDouble("amount");

                transactions.add(record);
            }
        }

        return transactions;
    }
}