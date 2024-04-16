package com.example.demo.service.impl;


import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Abonent;
import com.example.demo.model.Depts;
import com.example.demo.model.Transactions;
import com.example.demo.repository.AbonentRepository;
import com.example.demo.repository.DeptsRepository;
import com.example.demo.repository.TransactionsRepository;
import com.example.demo.service.TransactionsService;

@Service
public class TransactionsServiceImpl implements TransactionsService{
    private TransactionsRepository transactionsRepository;
    private DeptsRepository deptsRepository;
    private AbonentRepository abonentRepository;

    public TransactionsServiceImpl(TransactionsRepository transactionsRepository, DeptsRepository deptsRepository, AbonentRepository abonentRepository) {
        this.transactionsRepository = transactionsRepository;
        this.deptsRepository = deptsRepository;
        this.abonentRepository = abonentRepository;
    }

    @Override
    public Transactions addTransaction(Integer transationAmount, String abonentCode) {
        Abonent abonent = abonentRepository.findByAbonentCode(abonentCode);
        Transactions transaction = new Transactions();
        transaction.setTransactionAmount(transationAmount);
        transaction.setAbonent(abonent);
        transaction.setTransactionDate(LocalDateTime.now());
        
        List<Depts> deptsList = deptsRepository.findByAbonent(abonent);
        Depts latestDept = new Depts();
        if (!deptsList.isEmpty()) {
            latestDept = deptsList.stream()
                    .max(Comparator.comparing(Depts::getDate))
                    .orElse(null);
        }
        
        Depts updatedDept = new Depts();
        updatedDept.setRemainingDept(latestDept.getRemainingDept() - transationAmount);
        updatedDept.setDate(LocalDateTime.now());
        updatedDept.setAbonent(abonent);
        transactionsRepository.save(transaction);
        deptsRepository.save(updatedDept);
        return transaction;
    }

    @Override
    public List<Transactions> getTransactionsByAbonentCode(String abonentCode) {
        return transactionsRepository.findByAbonent_AbonentCode(abonentCode);
    }
    
}
