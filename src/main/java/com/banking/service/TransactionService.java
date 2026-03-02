package com.banking.service;

import com.banking.dao.TransactionDAO;
import com.banking.util.DBConnection;

import java.sql.Connection;
import java.util.List;

public class TransactionService {

    public List<String> fetchTransactions(int accountId, String type) throws Exception {

        try (Connection conn = DBConnection.getConnection()) {

            TransactionDAO dao = new TransactionDAO();

            return dao.getTransactions(conn, accountId, type);
        }
    }
}