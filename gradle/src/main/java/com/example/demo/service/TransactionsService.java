package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Transactions;

public interface TransactionsService {
    Transactions addTransaction(Integer transationAmount, String abonentCode);
    List<Transactions> getTransactionsByAbonentCode(String abonentCode);
}
