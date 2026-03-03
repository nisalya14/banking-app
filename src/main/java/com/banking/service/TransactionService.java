package com.banking.service;

import com.banking.dao.TransactionDAO;
import com.banking.model.Transaction;
import com.banking.util.DBConnection;

import java.sql.Connection;
import java.util.List;

public class TransactionService {

	public List<Transaction> fetchTransactions(int accountId,
            String type,
            int page,
            int size) throws Exception {

if (!type.equalsIgnoreCase("DEBIT") &&
!type.equalsIgnoreCase("CREDIT")) {
throw new Exception("Invalid transaction type");
}

try (Connection conn = DBConnection.getConnection()) {

TransactionDAO dao = new TransactionDAO();
return dao.getTransactions(conn, accountId, type, page, size);
}
}
	}