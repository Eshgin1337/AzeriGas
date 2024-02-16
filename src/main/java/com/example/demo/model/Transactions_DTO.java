package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Transactions_DTO {
    
    // private long id;

    // private Integer transactionAmount;

    // private LocalDate transactionDate;

    // private String abonentCode; 

    private List<Transactions> transactionsLists;
}
