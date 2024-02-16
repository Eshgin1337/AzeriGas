package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Abonent;
import com.example.demo.model.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Long>{
    Transactions findByAbonent(Abonent abonent);
    List<Transactions> findByAbonent_AbonentCode(String abonentCode);
}
