package com.banking.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.banking.model.Transaction;

public class TransactionDAO {

	public List<Transaction> getTransactions(Connection conn,
            int accountId,
            String type,
            int page,
            int size) throws Exception {

int offset = (page - 1) * size;   // 🔥 NEW

String sql = "SELECT transaction_id, account_id, transaction_type, amount, created_at " +
"FROM transactions " +
"WHERE account_id = ? AND transaction_type = ? " +
"ORDER BY created_at DESC " +     // 🔥 NEW (History sorted latest first)
"LIMIT ? OFFSET ?";               // 🔥 NEW (Pagination)

List<Transaction> transactions = new ArrayList<>();

try (PreparedStatement ps = conn.prepareStatement(sql)) {

ps.setInt(1, accountId);
ps.setString(2, type.toUpperCase());
ps.setInt(3, size);        // 🔥 NEW
ps.setInt(4, offset);      // 🔥 NEW

ResultSet rs = ps.executeQuery();

while (rs.next()) {

Transaction t = new Transaction();
t.setTransactionId(rs.getInt("transaction_id"));
t.setAccountId(rs.getInt("account_id"));
t.setType(rs.getString("transaction_type"));
t.setAmount(rs.getDouble("amount"));
t.setTimestamp(rs.getTimestamp("created_at").toLocalDateTime());

transactions.add(t);
}
}

return transactions;
}
}